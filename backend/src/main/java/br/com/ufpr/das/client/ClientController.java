package br.com.ufpr.das.client;

import java.util.List;
import java.net.URI;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    @NonNull
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> insert(@Valid @RequestBody ClientDTO client) {
        String errorMessage = "Error insert Client ";
        try {
            ClientDTO clientSaved = this.clientService.insert(client);
            URI uriClient = URI.create("clients/" + clientSaved.getId());
            return ResponseEntity.created(uriClient).body(clientSaved);
        } catch (IllegalArgumentException e) {
            return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        try {
            return ResponseEntity.ok(this.clientService.findAll());
        } catch (Exception e) {
            log.error("Error findAll Client ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        String errorText = "Error findById Client ";
        try {
            return ResponseEntity.ok(this.clientService.findById(id));
        } catch (IllegalArgumentException e) {
            log.error(errorText, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EntityNotFoundException e) {
            log.error(errorText, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(errorText, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        String errorMessage = "Error deleteById Client ";
        try {
            this.clientService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EntityNotFoundException e) {
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ClientDTO> update(
        @PathVariable Long id,
        @Valid @RequestBody ClientDTO client
    ) {
        String errorMessage = "Error update Client ";
        try {
            return ResponseEntity.ok(this.clientService.update(id, client));
        } catch (IllegalArgumentException e) {
            return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return handleException(errorMessage, e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ClientDTO> handleException(
        String errorMessage, 
        Exception exception,
        HttpStatus httpStatus
    ) {
        log.error(errorMessage, exception);
        return ResponseEntity.status(httpStatus).build();
    }
    
}
