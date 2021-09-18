package br.com.ufpr.das.client;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertEquals(result.getBody().getId(), clientSaved.getId());
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
