package br.com.ufpr.das.orderItem;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/order-items")
@RequiredArgsConstructor
@Slf4j
public class OrderItemController {

    @NonNull
    private OrderItemService orderItemService;

    @GetMapping("by-order-id/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> findByOrderId(
        @PathVariable Long orderId
    ) {
        try {
            return ResponseEntity.ok(this.orderItemService.findByOrderId(orderId));
        } catch (Exception e) {
            log.error("Error findByOrderId OrderItem ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> insert(@Valid @RequestBody OrderItemDTO orderItem) {
      String errorMessage = "Error insert Order Item ";
      try {
        OrderItemDTO orderSaved = this.orderItemService.insert(orderItem);
        URI uriOrder = URI.create("order-items/" + orderSaved.getId());
        return ResponseEntity.created(uriOrder).body(orderSaved);
      } catch (IllegalArgumentException e) {
        return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    private ResponseEntity<OrderItemDTO> handleException(
        String errorMessage, 
        Exception exception,
        HttpStatus httpStatus) {
      log.error(errorMessage, exception);
      return ResponseEntity.status(httpStatus).build();
    }

    
    @PutMapping("{id}")
    public ResponseEntity<OrderItemDTO> update(@PathVariable Long id, @Valid @RequestBody OrderItemDTO orderItemDTO) {
        String errorMessage = "Error on update produduct.";
        try {
            return ResponseEntity.ok(this.orderItemService.update(id, orderItemDTO));
        } catch (IllegalArgumentException e) {
            return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return handleException(errorMessage, e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
