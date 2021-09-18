package br.com.ufpr.das.client;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @InjectMocks private ClientController controller;

    @Mock private ClientService clientService;

    @Test
    public void testInstance() {
        assertNotNull(controller);
    }

    @Test
    public void testInsert() {
        ClientDTO client = ClientDTOFactory.getOne("idNull");
        ClientDTO clientSaved = ClientDTOFactory.getOne("default");
        when(clientService.insert(client)).thenReturn(clientSaved);
        ResponseEntity<ClientDTO> result = controller.insert(client);
        verify(clientService).insert(client);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(clientSaved.getId(), result.getBody().getId());
    }

    @Test
    public void testInsertIdNotNull() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        when(clientService.insert(client)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ClientDTO> result = controller.insert(client);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testInsertCpfNull() {
        ClientDTO client = ClientDTOFactory.getOne("cpfNull");
        when(clientService.insert(client)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ClientDTO> result = controller.insert(client);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testFindAll() {
        List<ClientDTO> clients = ClientDTOFactory.getList(5, "default");
        when(clientService.findAll()).thenReturn(clients);
        ResponseEntity<List<ClientDTO>> result = controller.findAll();
        verify(clientService).findAll();
        assertNotNull(result.getBody());
        assertEquals(5, result.getBody().size());
    }

    @Test
    public void testFindAllEmptyList() {
        when(clientService.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<ClientDTO>> result = controller.findAll();
        verify(clientService).findAll();
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
    }

    @Test
    public void testFindAllGenericException() {
        when(clientService.findAll()).thenThrow(new RuntimeException());
        ResponseEntity<List<ClientDTO>> result = controller.findAll();
        verify(clientService).findAll();
        assertNull(result.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testFindById() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        when(clientService.findById(1L)).thenReturn(client);
        ResponseEntity<ClientDTO> result = controller.findById(1L);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(client.getId(), result.getBody().getId());
    }

    @Test
    public void testFindById_IdNull() {
        when(clientService.findById(null)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ClientDTO> result = controller.findById(null);
        assertNull(result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testFindByIdClientNotFound() {
        when(clientService.findById(200L)).thenThrow(new EntityNotFoundException());
        ResponseEntity<ClientDTO> result = controller.findById(200L);
        assertNull(result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteById() {
        ResponseEntity<Object> result = controller.deleteById(1L);
        verify(clientService).deleteById(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testDeleteById_IdNull() {
        doThrow(new IllegalArgumentException()).when(clientService).deleteById(null);
        ResponseEntity<Object> result = controller.deleteById(null);
        verify(clientService).deleteById(null);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testDeleteByIdClientNotFound() {
        doThrow(new EntityNotFoundException()).when(clientService).deleteById(1000L);
        ResponseEntity<Object> result = controller.deleteById(1000L);
        verify(clientService).deleteById(1000L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdate() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        when(clientService.update(client.getId(), client)).thenReturn(client);
        ResponseEntity<ClientDTO> result = controller.update(client.getId(), client);
        verify(clientService).update(client.getId(), client);
        assertNotNull(result.getBody());
        assertEquals(result.getBody().getId(), client.getId());
    }

    @Test
    public void testUpdateIdNull() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        when(clientService.update(null, client)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ClientDTO> result = controller.update(null, client);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testUpdateClientIdNull() {
        ClientDTO client = ClientDTOFactory.getOne("idNull");
        when(clientService.update(1L, client)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ClientDTO> result = controller.update(1L, client);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testUpdateClientNotFound() {
        ClientDTO client = ClientDTOFactory.getOne("default");
        when(clientService.update(100L, client)).thenThrow(new EntityNotFoundException());
        ResponseEntity<ClientDTO> result = controller.update(100L, client);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
    
}
