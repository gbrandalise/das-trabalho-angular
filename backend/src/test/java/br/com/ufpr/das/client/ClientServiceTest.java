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
    public void testDeleteById() {
        Client clientEntity = ClientFactory.getOne("default");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        service.deleteById(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteById_IdNull() {
        service.deleteById(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteByIdClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        service.deleteById(1L);
    }
    
}
