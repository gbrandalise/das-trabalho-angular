package br.com.ufpr.das.client;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ClientDTO {

    private Long id;
    @NotNull
    private String cpf;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    
}
