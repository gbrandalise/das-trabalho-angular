package br.com.ufpr.das.orderItem;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
