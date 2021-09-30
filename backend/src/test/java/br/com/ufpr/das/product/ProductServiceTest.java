package br.com.ufpr.das.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
}
