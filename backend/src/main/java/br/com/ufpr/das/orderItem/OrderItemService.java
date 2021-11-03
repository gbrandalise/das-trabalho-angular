package br.com.ufpr.das.orderItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {

  @NonNull
  private OrderItemRepository orderItemRepository;

  public List<OrderItemDTO> findByOrderId(Long orderId) {
    List<OrderItem> orderItems = this.orderItemRepository.findByOrderId(orderId);
    return orderItems.stream()
      .map(OrderItemMapper.INSTANCE::toDTO)
      .collect(Collectors.toList());
  }

  public OrderItemDTO insert(OrderItemDTO orderItem) {
    this.validateInsert(orderItem);
    return this.save(orderItem);
  }

  private OrderItemDTO save(OrderItemDTO orderItem) {
    this.validate(orderItem);
    OrderItem orderItemEntity = OrderItemMapper.INSTANCE.toEntity(orderItem);
    orderItemEntity = this.orderItemRepository.save(orderItemEntity);
    return OrderItemMapper.INSTANCE.toDTO(orderItemEntity);
  }

  private void validateInsert(OrderItemDTO orderItem) {
    if (orderItem.getId() != null) {
      throw new IllegalArgumentException("ID deve ser nulo ao inserir novo item de pedido.");
    }
  }

  private void validate(OrderItemDTO orderItem) throws IllegalArgumentException {
    Set<ConstraintViolation<OrderItemDTO>> violations = Validation
      .buildDefaultValidatorFactory()
      .getValidator()
      .validate(orderItem);
    if (!violations.isEmpty()) {
      throw new IllegalArgumentException("Valores inválidos");
    }
  }
  
}
