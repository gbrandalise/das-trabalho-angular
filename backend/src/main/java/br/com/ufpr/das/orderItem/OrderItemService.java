package br.com.ufpr.das.orderItem;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
  
}
