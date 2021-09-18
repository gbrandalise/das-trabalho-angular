package br.com.ufpr.das.client;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return ResponseEntity.ok(this.clientService.insert(client));
        } catch (IllegalArgumentException e) {
            return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ClientDTO> update(
        @PathVariable Long id,
        @RequestBody ClientDTO client
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
