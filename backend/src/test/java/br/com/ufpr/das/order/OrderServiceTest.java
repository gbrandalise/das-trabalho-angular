package br.com.ufpr.das.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @InjectMocks
  private OrderService service;

  @Mock
  private OrderRepository orderRepository;

  @Test
  public void testInstance() {
    assertNotNull(service);
  }

  @Test
  public void testInsert() {
    OrderDTO order = OrderDTOFactory.getOne("idNull");
    Order orderEntity = OrderFaqctory.getOne("default");
    when(orderRepository.save(ArgumentMatchers.any())).thenReturn(orderEntity);
    OrderDTO result = service.insert(order);
    verify(orderRepository).save(ArgumentMatchers.any());
    assertNotNull(result);
    assertEquals(result.getId(), orderEntity.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInsertIdNotNull() {
    OrderDTO order = OrderDTOFactory.getOne("default");
    service.insert(order);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInsertDateNull() {
    OrderDTO order = OrderDTOFactory.getOne("dateNull");
    service.insert(order);
  }

}
