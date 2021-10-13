package br.com.ufpr.das.client;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

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
    public void testFindAll() {
        List<Client> clientEntities = ClientFactory.getList(5, "default");
        when(clientRepository.findAll()).thenReturn(clientEntities);
        List<ClientDTO> result = service.findAll();
        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testFindAllEmptyList() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());
        List<ClientDTO> result = service.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindById() {
        Client clientEntity = ClientFactory.getOne("default");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        ClientDTO result = service.findById(1L);
        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindById_IdNull() {
        service.findById(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindByIdClientNotFound() {
        when(clientRepository.findById(200L)).thenReturn(Optional.empty());
        service.findById(200L);
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
