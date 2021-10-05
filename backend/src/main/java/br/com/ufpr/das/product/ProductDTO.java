package br.com.ufpr.das.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    @NotNull
    @NotBlank
    private String description;
}
