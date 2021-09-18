package br.com.ufpr.das.client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
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

    public List<ClientDTO> findAll() {
        List<Client> clients = this.clientRepository.findAll();
        return clients.stream()
            .map(ClientMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }

    public ClientDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não deve ser nulo ao buscar um cliente");
        }
        Client client = this.clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        return ClientMapper.INSTANCE.toDTO(client);
    }

    public void deleteById(Long id) {
        this.validateDelete(id);
        this.clientRepository.deleteById(id);
    }

    private void validateDelete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        this.clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    public ClientDTO update (Long id, ClientDTO client) {
        validateUpdate(id, client);
        return this.save(client);
    }

    private void validateUpdate(Long id, ClientDTO client) {
        if (id == null
            || client.getId() == null
            || !id.equals(client.getId())) {
            throw new IllegalArgumentException("ID a ser atualizado não corresponde aos dados do cliente");
        }
        this.clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }
    
}
