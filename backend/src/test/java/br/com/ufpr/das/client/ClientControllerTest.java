package br.com.ufpr.das.client;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
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
    
}
