package br.com.ufpr.das.product;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    @NonNull
    private ProductService productService;
    
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO product){
        String errorMessage = "Error insert Product ";
        try {
            ProductDTO productSaved = this.productService.insert(product);
            URI uriClient = URI.create("products/" + productSaved.getId());
            return ResponseEntity.created(uriClient).body(productSaved);
        } catch (IllegalArgumentException e) {
            return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        try {
            return ResponseEntity.ok(this.productService.findAll());
        } catch (Exception e) {
            log.error("Error findAll Product ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        String errorText = "Error findById Product ";
        try {
            return ResponseEntity.ok(this.productService.findById(id));
        } catch (IllegalArgumentException e) {
            return handleException(errorText, e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return handleException(errorText, e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleException(errorText, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private ResponseEntity<ProductDTO> handleException(
        String errorMessage, 
        Exception exception,
        HttpStatus httpStatus
    ) {
        log.error(errorMessage, exception);
        return ResponseEntity.status(httpStatus).build();
    }
}