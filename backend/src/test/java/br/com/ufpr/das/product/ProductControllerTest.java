package br.com.ufpr.das.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    
    @InjectMocks private ProductController controller;

    @Mock private ProductService productService;

    @Test
    public void testInstance(){
        assertNotNull(controller);
    }

    @Test
    public void testInsert(){

        ProductDTO product = ProductDTOFactory.getOne("idNull");
        ProductDTO productSaved = ProductDTOFactory.getOne("default");
        when(productService.insert(product)).thenReturn(productSaved);
        ResponseEntity<ProductDTO> result = controller.insert(product);
        verify(productService).insert(product);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(productSaved.getId(), result.getBody().getId());
    }


    @Test
    public void testFindAll() {
        List<ProductDTO> productsDto = ProductDTOFactory.getList(5, "default");
        when(productService.findAll()).thenReturn(productsDto);
        ResponseEntity<List<ProductDTO>> result = controller.findAll();
        verify(productService).findAll();
        assertNotNull(result.getBody());
        assertEquals(5, result.getBody().size());
    }

    @Test
    public void testFindAllEmptyList() {
        when(productService.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<ProductDTO>> result = controller.findAll();
        verify(productService).findAll();
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
    }

    @Test
    public void testFindAllGenericException() {
        when(productService.findAll()).thenThrow(new RuntimeException());
        ResponseEntity<List<ProductDTO>> result = controller.findAll();
        verify(productService).findAll();
        assertNull(result.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testFindById() {
        ProductDTO product = ProductDTOFactory.getOne("default");
        when(productService.findById(1L)).thenReturn(product);
        ResponseEntity<ProductDTO> result = controller.findById(1L);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(product.getId(), result.getBody().getId());
    }

    @Test
    public void testFindById_IdNull() {
        when(productService.findById(null)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ProductDTO> result = controller.findById(null);
        assertNull(result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testFindByIdProductNotFound() {
        when(productService.findById(200L)).thenThrow(new EntityNotFoundException());
        ResponseEntity<ProductDTO> result = controller.findById(200L);
        assertNull(result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteById() {
        ResponseEntity<Object> result = controller.deleteById(1L);
        verify(productService).deleteById(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testDeleteById_IdNull() {
        doThrow(new IllegalArgumentException()).when(productService).deleteById(null);
        ResponseEntity<Object> result = controller.deleteById(null);
        verify(productService).deleteById(null);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testDeleteByIdProductNotFound() {
        doThrow(new EntityNotFoundException()).when(productService).deleteById(1000L);
        ResponseEntity<Object> result = controller.deleteById(1000L);
        verify(productService).deleteById(1000L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

}
