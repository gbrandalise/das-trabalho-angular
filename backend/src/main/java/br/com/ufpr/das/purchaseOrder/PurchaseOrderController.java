package br.com.ufpr.das.purchaseOrder;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("purchase-orders")
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderController {

  @NonNull
  private PurchaseOrderService service;

  @PostMapping
  public ResponseEntity<PurchaseOrderDTO> insert(@Valid @RequestBody PurchaseOrderDTO order) {
    String errorMessage = "Error insert Order ";
    try {
      PurchaseOrderDTO orderSaved = this.service.insert(order);
      URI uriOrder = URI.create("orders/" + orderSaved.getId());
      return ResponseEntity.created(uriOrder).body(orderSaved);
    } catch (IllegalArgumentException e) {
      return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<List<PurchaseOrderDTO>> findAll() {
    try {
      return ResponseEntity.ok(this.service.findAll());
    } catch (Exception e) {
      log.error("Error findAll PurchaseOrder ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<PurchaseOrderDTO> findById(@PathVariable Long id) {
    String errorText = "Error findById Client ";
    try {
      return ResponseEntity.ok(this.service.findById(id));
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

  @GetMapping("by-cpf/{cpf}")
  public ResponseEntity<List<PurchaseOrderDTO>> findByClientCpf(
    @PathVariable String cpf
  ) {
    try {
      return ResponseEntity.ok(this.service.findByClientCpf(cpf));
    } catch (Exception e) {
      log.error("Error findByClientCpf PurchaseOrder ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private ResponseEntity<PurchaseOrderDTO> handleException(String errorMessage, Exception exception,
      HttpStatus httpStatus) {
    log.error(errorMessage, exception);
    return ResponseEntity.status(httpStatus).build();
  }

  
  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteById(@PathVariable Long id) {
      String errorMessage = "Error deleteById Product ";
      try {
          this.service.deleteById(id);
          return ResponseEntity.ok().build();
      } catch (ValidationException e) {
          log.error(errorMessage, e);
          return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
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
    public ResponseEntity<PurchaseOrderDTO> update(
        @PathVariable Long id,
        @Valid @RequestBody PurchaseOrderDTO order
    ) {
        String errorMessage = "Error update Order ";
        try {
            return ResponseEntity.ok(this.service.update(id, order));
        } catch (IllegalArgumentException e) {
            return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return handleException(errorMessage, e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
