package br.com.ufpr.das.product;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("products")
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


    private ResponseEntity<ProductDTO> handleException(
        String errorMessage, 
        Exception exception,
        HttpStatus httpStatus
    ) {
        log.error(errorMessage, exception);
        return ResponseEntity.status(httpStatus).build();
    }
}
