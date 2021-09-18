package br.com.ufpr.das.client;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @InjectMocks private ClientService service;

    @Mock private ClientRepository clientRepository;

    @Test
    public void testInstance() {
        assertNotNull(service);
    }

    @Test
    public void testInsert() {
        ClientDTO client = ClientDTOFactory.getOne("idNull");
        Client clientEntity = ClientFactory.getOne("default");
        when(clientRepository.save(ArgumentMatchers.any())).thenReturn(clientEntity);
        ClientDTO result = service.insert(client);
        verify(clientRepository).save(ArgumentMatchers.any());
        assertNotNull(result);
        assertEquals(result.getId(), clientEntity.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertIdNotNull() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        service.insert(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertCpfNull() {
        ClientDTO client = ClientDTOFactory.getOne("cpfNull");
        service.insert(client);
    }

    @Test
    public void testUpdate() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        Client clientEntity = ClientMapper.INSTANCE.toEntity(client);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(clientEntity));
        when(clientRepository.save(ArgumentMatchers.any())).thenReturn(clientEntity);
        ClientDTO result = service.update(client.getId(), client);
        verify(clientRepository).save(ArgumentMatchers.any());
        assertNotNull(result);
        assertEquals(client.getId(), result.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateIdDifferentFromParameter() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        service.update(1000L, client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateIdNull() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        service.update(null, client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateClientIdNull() {
        ClientDTO client = ClientDTOFactory.getOne("idNull");
        service.update(1000L, client);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateClientNotFound() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());
        service.update(client.getId(), client);
    }
    
}
