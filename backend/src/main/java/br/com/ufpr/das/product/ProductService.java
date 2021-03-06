package br.com.ufpr.das.product;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
            throw new ValidationException("ID deve ser nulo ao inserir novo produto.");
        }
    }

    private ProductDTO save(ProductDTO product){
        this.validate(product);
        Product productEntity = ProductMapper.INSTANCE.toEntity(product);
        productEntity = this.productRepository.save(productEntity);
        return ProductMapper.INSTANCE.toDTO(productEntity);
    }

    private void validate(ProductDTO product) {
        Set<ConstraintViolation<ProductDTO>> violations = Validation
            .buildDefaultValidatorFactory()
            .getValidator()
            .validate(product);
        if (!violations.isEmpty()) {
            throw new ValidationException("Valores inválidos");
        }
    }

    public List<ProductDTO> findAll(){
        List<Product> products = this.productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return products.stream()
            .map(ProductMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());

    }

    public ProductDTO findById(Long id){
        if (id == null) {
            throw new ValidationException("ID não deve ser nulo ao buscar um produto");
        }
        Product product = this.productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
        return ProductMapper.INSTANCE.toDTO(product);
    }


    public void deleteById(Long id){
        this.validateDelete(id);
        this.productRepository.deleteById(id);
    }

    public ProductDTO update (Long id, ProductDTO product) {
        validateUpdate(id, product);
        return this.save(product);
    }

    private void validateDelete(Long id) {
        if (id == null) {
            throw new ValidationException("ID não pode ser nulo");
        }
        this.productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    private void validateUpdate(Long id, ProductDTO product) {
        if (id == null
            || product.getId() == null
            || !id.equals(product.getId())) {
            throw new ValidationException("ID a ser atualizado não corresponde aos dados de um produto");
        }
        this.productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não endontrado"));
    }
}
