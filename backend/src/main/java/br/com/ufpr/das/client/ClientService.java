package br.com.ufpr.das.client;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    @NonNull
    private ClientRepository clientRepository;

    public ClientDTO insert(ClientDTO client) {
        this.validateInsert(client);
        return this.save(client);
    }

    private void validateInsert(ClientDTO client) {
        if (client.getId() != null) {
            throw new IllegalArgumentException("ID deve ser nulo ao inserir novo cliente");
        }
    }

    private ClientDTO save(ClientDTO client) {
        this.validate(client);
        Client clientEntity = ClientMapper.INSTANCE.toEntity(client);
        clientEntity = this.clientRepository.save(clientEntity);
        return ClientMapper.INSTANCE.toDTO(clientEntity);
    }

    private void validate(ClientDTO client) throws IllegalArgumentException {
        Set<ConstraintViolation<ClientDTO>> violations = Validation
            .buildDefaultValidatorFactory()
            .getValidator()
            .validate(client);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Valores inválidos");
        }
    }
    
}
