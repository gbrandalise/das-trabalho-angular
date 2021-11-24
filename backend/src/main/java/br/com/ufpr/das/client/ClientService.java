package br.com.ufpr.das.client;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private static final String ERROR_MESSAGE_NOT_FOUND = "Cliente não encontrado";

    @NonNull
    private ClientRepository clientRepository;

    public ClientDTO insert(ClientDTO client) {
        this.validateInsert(client);
        return this.save(client);
    }

    private void validateInsert(ClientDTO client) {
        if (client.getId() != null) {
            throw new ValidationException("ID deve ser nulo ao inserir novo cliente");
        }
    }

    private ClientDTO save(ClientDTO client) {
        this.validate(client);
        Client clientEntity = ClientMapper.INSTANCE.toEntity(client);
        clientEntity = this.clientRepository.save(clientEntity);
        return ClientMapper.INSTANCE.toDTO(clientEntity);
    }

    private void validate(ClientDTO client) {
        Set<ConstraintViolation<ClientDTO>> violations = Validation
            .buildDefaultValidatorFactory()
            .getValidator()
            .validate(client);
        if (!violations.isEmpty()) {
            throw new ValidationException("Valores inválidos");
        }
        this.validateCpfExists(client);
    }

    private void validateCpfExists(ClientDTO client) {
        Optional<Client> clientEntity = this.clientRepository.findByCpf(client.getCpf());
        if (clientEntity.isPresent()
            && (client.getId() == null
                || (client.getId() != null
                    && !client.getId().equals(clientEntity.get().getId())))) {
            throw new ValidationException("CPF já cadastrado");
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
            throw new ValidationException("ID não deve ser nulo ao buscar um cliente");
        }
        Client client = this.clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE_NOT_FOUND));
        return ClientMapper.INSTANCE.toDTO(client);
    }

    public void deleteById(Long id) {
        this.validateDelete(id);
        this.clientRepository.deleteById(id);
    }

    private void validateDelete(Long id) {
        if (id == null) {
            throw new ValidationException("ID não pode ser nulo");
        }
        Client client = this.clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE_NOT_FOUND));
        this.hasOrders(client);
    }

    private void hasOrders(Client client) {
        if (client.getOrders() != null
            && !client.getOrders().isEmpty()) {
            throw new ValidationException("Cliente possui pedidos. Não é possível excluir");
        }
    }

    public ClientDTO update (Long id, ClientDTO client) {
        validateUpdate(id, client);
        return this.save(client);
    }

    private void validateUpdate(Long id, ClientDTO client) {
        if (id == null
            || client.getId() == null
            || !id.equals(client.getId())) {
            throw new ValidationException("ID a ser atualizado não corresponde aos dados do cliente");
        }
        this.clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE_NOT_FOUND));
    }
    
}
