package br.com.ufpr.das.product;

import javax.transaction.Transactional;
import java.util.Set;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    
    @NonNull
    private ProductRepository productRepository;

    public ProductDTO insert(ProductDTO product){
        this.validateInsert(product);
        return this.save(product);

    }

    private void validateInsert(ProductDTO product){
        if(product.getId() != null){
            throw new IllegalArgumentException("ID deve ser nulo ao inserir novo produto.");
        }
    }

    private ProductDTO save(ProductDTO product){
        this.validate(product);
        Product productEntity = ProductMapper.INSTANCE.toEntity(product);
        productEntity = this.productRepository.save(productEntity);
        return ProductMapper.INSTANCE.toDTO(productEntity);
    }

    private void validate(ProductDTO product) throws IllegalArgumentException{
        Set<ConstraintViolation<ProductDTO>> violations = Validation
            .buildDefaultValidatorFactory()
            .getValidator()
            .validate(product);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Valores inválidos");
        }
    }

    
}
