package br.com.ufpr.das.purchaseOrder;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseOrderService {

  @NonNull
  private PurchaseOrderRepository orderRepository;

  public PurchaseOrderDTO insert(PurchaseOrderDTO order) {
    this.validateInsert(order);
    return this.save(order);
  }

  private PurchaseOrderDTO save(PurchaseOrderDTO order) {
    this.validate(order);
    PurchaseOrder orderEntity = PurchaseOrderMapper.INSTANCE.toEntity(order);
    orderEntity = this.orderRepository.save(orderEntity);
    return PurchaseOrderMapper.INSTANCE.toDTO(orderEntity);
  }

  private void validateInsert(PurchaseOrderDTO order) {
    if (order.getId() != null) {
      throw new IllegalArgumentException("ID deve ser nulo ao inserir novo pedido.");
    }
  }

  private void validate(PurchaseOrderDTO order) throws IllegalArgumentException {
    Set<ConstraintViolation<PurchaseOrderDTO>> violations = Validation
                                                              .buildDefaultValidatorFactory()
                                                              .getValidator()
                                                              .validate(order);
    if (!violations.isEmpty()) {
      throw new IllegalArgumentException("Valores inválidos");
    }
  }

}
