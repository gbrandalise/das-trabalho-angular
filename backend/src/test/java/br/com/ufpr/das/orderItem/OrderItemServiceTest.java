package br.com.ufpr.das.orderItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

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
    when(orderItemRepository.findByOrderId(1L, Sort.by(Sort.Direction.ASC, "id"))).thenReturn(orderItemEntities);
    List<OrderItemDTO> result = service.findByOrderId(1L);
    assertNotNull(result);
    assertEquals(5, result.size());
  }

  @Test
  public void testFindByOrderIdEmptyList() {
    when(orderItemRepository.findByOrderId(1L, Sort.by(Sort.Direction.ASC, "id"))).thenReturn(Collections.emptyList());
    List<OrderItemDTO> result = service.findByOrderId(1L);
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testInsert() {
    OrderItemDTO orderItem = OrderItemDTOFactory.getOne("idNull");
    OrderItem orderItemEntity = OrderItemFactory.getOne("default");
    when(orderItemRepository.save(ArgumentMatchers.any())).thenReturn(orderItemEntity);
    OrderItemDTO result = service.insert(orderItem);
    verify(orderItemRepository).save(ArgumentMatchers.any());
    assertNotNull(result);
    assertEquals(result.getId(), orderItemEntity.getId());
  }

  @Test(expected = ValidationException.class)
  public void testInsertIdNotNull() {
    OrderItemDTO order = OrderItemDTOFactory.getOne("default");
    service.insert(order);
  }

  @Test(expected = ValidationException.class)
  public void testInsertQuantityNull() {
    OrderItemDTO order = OrderItemDTOFactory.getOne("quantityNull");
    service.insert(order);
  }

}
