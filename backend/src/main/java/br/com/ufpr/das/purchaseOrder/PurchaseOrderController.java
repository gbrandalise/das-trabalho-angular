package br.com.ufpr.das.purchaseOrder;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("purchase_orders")
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

  private ResponseEntity<PurchaseOrderDTO> handleException(String errorMessage, Exception exception,
      HttpStatus httpStatus) {
    log.error(errorMessage, exception);
    return ResponseEntity.status(httpStatus).build();
  }
}
