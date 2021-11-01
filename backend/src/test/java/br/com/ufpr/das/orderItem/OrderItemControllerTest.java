package br.com.ufpr.das.orderItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemControllerTest {

  @InjectMocks
  private OrderItemController controller;

  @Mock
  private OrderItemService orderItemService;

  @Test
  public void testInstance() {
    assertNotNull(controller);
  }

  @Test
  public void testFindByOrderId() {
    List<OrderItemDTO> orderItemsDto = OrderItemDTOFactory.getList(5, "default");
    when(orderItemService.findByOrderId(1L)).thenReturn(orderItemsDto);
    ResponseEntity<List<OrderItemDTO>> result = controller.findByOrderId(1L);
    verify(orderItemService).findByOrderId(1L);
    assertNotNull(result.getBody());
    assertEquals(5, result.getBody().size());
  }

  @Test
  public void testFindByOrderIdEmptyList() {
    when(orderItemService.findByOrderId(1L)).thenReturn(Collections.emptyList());
    ResponseEntity<List<OrderItemDTO>> result = controller.findByOrderId(1L);
    verify(orderItemService).findByOrderId(1L);
    assertNotNull(result.getBody());
    assertTrue(result.getBody().isEmpty());
  }
}
