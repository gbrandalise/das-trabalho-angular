package br.com.ufpr.das.product;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ProductDTO> findAll(){
        List<Product> products = this.productRepository.findAll();
        return products.stream()
            .map(ProductMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());

    }

    public ProductDTO findById(Long id){
        if(id == null){
            throw new IllegalArgumentException("ID não deve ser nulo ao buscar um produto");
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
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        this.productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    private void validateUpdate(Long id, ProductDTO product) {
        if (id == null
            || product.getId() == null
            || !id.equals(product.getId())) {
            throw new IllegalArgumentException("ID a ser atualizado não corresponde aos dados de um produto");
        }
        this.productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não endontrado"));
    }
}
