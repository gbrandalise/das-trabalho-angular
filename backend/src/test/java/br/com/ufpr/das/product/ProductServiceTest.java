package br.com.ufpr.das.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks private ProductService service;

    @Mock private ProductRepository productRepository;

    @Test
    public void testInstance(){
        assertNotNull(service);
    }

    @Test
    public void testInsert(){
        ProductDTO productDTO = ProductDTOFactory.getOne("idNull");
        Product product = ProductFactory.getOne("default");
        when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
        ProductDTO result = service.insert(productDTO);
        verify(productRepository).save(ArgumentMatchers.any());
        assertNotNull(result);
        assertEquals(result.getId(), product.getId());

    }

    @Test(expected = ValidationException.class)
    public void testInsertIdNotNull(){
        ProductDTO product = ProductDTOFactory.getOne("default");
        service.insert(product);
    }

    @Test(expected = ValidationException.class)
    public void testInsertDescriptionNull(){
        ProductDTO product = ProductDTOFactory.getOne("descriptionNull");
        service.insert(product);

    }

    @Test(expected = ValidationException.class)
    public void testInsertDescriptionBlank(){
        ProductDTO product = ProductDTOFactory.getOne("descriptionBlank");
        service.insert(product);

    }

    @Test
    public void testFindAll() {
        List<Product> productEntities = ProductFactory.getList(5, "default");
        when(productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(productEntities);
        List<ProductDTO> result = service.findAll();
        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testFindAllEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<ProductDTO> result = service.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindById() {
        Product productEntity = ProductFactory.getOne("default");
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        ProductDTO result = service.findById(1L);
        assertNotNull(result);
    }

    @Test(expected = ValidationException.class)
    public void testFindById_IdNull() {
        service.findById(null);
    }

    @Test
    public void testDeleteById() {
        Product productEntity = ProductFactory.getOne("default");
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        service.deleteById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test(expected = ValidationException.class)
    public void testDeleteById_IdNull() {
        service.deleteById(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteByIdProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        service.deleteById(1L);
    }

    @Test
    public void testUpdate() {
        ProductDTO product = ProductDTOFactory.getOne("default");
        Product productEntity = ProductMapper.INSTANCE.toEntity(product);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(productEntity));
        when(productRepository.save(ArgumentMatchers.any())).thenReturn(productEntity);
        ProductDTO result = service.update(product.getId(), product);
        verify(productRepository).save(ArgumentMatchers.any());
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
    }

    @Test(expected = ValidationException.class)
    public void testUpdateIdDifferentFromParameter() {
        ProductDTO product = ProductDTOFactory.getOne("default");
        service.update(1000L, product);
    }

    @Test(expected = ValidationException.class)
    public void testUpdateIdNull() {
        ProductDTO product = ProductDTOFactory.getOne("default");
        service.update(null, product);
    }

    @Test(expected = ValidationException.class)
    public void testUpdateClientIdNull() {
        ProductDTO client = ProductDTOFactory.getOne("idNull");
        service.update(1000L, client);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateClientNotFound() {
        ProductDTO product = ProductDTOFactory.getOne("default");
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        service.update(product.getId(), product);
    }

}
