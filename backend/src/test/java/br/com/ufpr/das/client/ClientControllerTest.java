package br.com.ufpr.das.client;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
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
    
}
