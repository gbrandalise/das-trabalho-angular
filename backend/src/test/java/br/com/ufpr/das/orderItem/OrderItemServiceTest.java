package br.com.ufpr.das.orderItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemServiceTest {

  @InjectMocks
  private OrderItemService service;

  @Mock
  private OrderItemRepository orderItemRepository;

  @Test
  public void testInstance() {
    assertNotNull(service);
  }

  @Test
  public void testFindByOrderId() {
    List<OrderItem> orderItemEntities = OrderItemFactory.getList(5, "default");
    when(orderItemRepository.findByOrderId(1L)).thenReturn(orderItemEntities);
    List<OrderItemDTO> result = service.findByOrderId(1L);
    assertNotNull(result);
    assertEquals(5, result.size());
  }

  @Test
  public void testFindByOrderIdEmptyList() {
    when(orderItemRepository.findByOrderId(1L)).thenReturn(Collections.emptyList());
    List<OrderItemDTO> result = service.findByOrderId(1L);
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

}
