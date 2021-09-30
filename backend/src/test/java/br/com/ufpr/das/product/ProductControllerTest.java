package br.com.ufpr.das.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

}
