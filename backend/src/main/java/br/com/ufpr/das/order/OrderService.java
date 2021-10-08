package br.com.ufpr.das.order;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  @NonNull
  private OrderRepository orderRepository;

  public OrderDTO insert(OrderDTO order) {
    this.validateInsert(order);
    return this.save(order);
  }

  private OrderDTO save(OrderDTO order) {
    // this.validate(order);
    Order orderEntity = OrderMapper.INSTANCE.toEntity(order);
    orderEntity = this.orderRepository.save(orderEntity);
    return OrderMapper.INSTANCE.toDTO(orderEntity);
  }

  private void validateInsert(OrderDTO order) {
    if (order.getId() != null) {
      throw new IllegalArgumentException("ID deve ser nulo ao inserir novo pedido.");
    }
  }

}
