package br.com.ufpr.das.purchaseOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
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
  private PurchaseOrderRepository purchaseOrderRepository;

  public PurchaseOrderDTO insert(PurchaseOrderDTO order) {
    this.validateInsert(order);
    order.setDate(LocalDateTime.now());
    return this.save(order);
  }

  private PurchaseOrderDTO save(PurchaseOrderDTO order) {
    this.validate(order);
    PurchaseOrder orderEntity = PurchaseOrderMapper.INSTANCE.toEntity(order);
    orderEntity = this.purchaseOrderRepository.save(orderEntity);
    return PurchaseOrderMapper.INSTANCE.toDTO(orderEntity);
  }

  private void validateInsert(PurchaseOrderDTO order) {
    if (order.getId() != null) {
      throw new IllegalArgumentException("ID deve ser nulo ao inserir novo pedido.");
    }
  }

  public List<PurchaseOrderDTO> findAll() {
    List<PurchaseOrder> purchaseOrders = this.purchaseOrderRepository.findAll();
    return purchaseOrders.stream().map(PurchaseOrderMapper.INSTANCE::toDTO).collect(Collectors.toList());
  }

  public PurchaseOrderDTO findById(Long id) {
    if (id == null) {
        throw new IllegalArgumentException("ID não deve ser nulo ao buscar um cliente");
    }
    PurchaseOrder purchaseOrder = this.purchaseOrderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    return PurchaseOrderMapper.INSTANCE.toDTO(purchaseOrder);
  }

  private void validate(PurchaseOrderDTO order) throws IllegalArgumentException {
    Set<ConstraintViolation<PurchaseOrderDTO>> violations = Validation.buildDefaultValidatorFactory().getValidator()
        .validate(order);
    if (!violations.isEmpty()) {
      throw new IllegalArgumentException("Valores inválidos");
    }
  }

  public List<PurchaseOrderDTO> findByClientCpf(String cpf) {
    List<PurchaseOrder> purchaseOrders = this.purchaseOrderRepository.findByClientCpf(cpf);
    return purchaseOrders.stream()
      .map(PurchaseOrderMapper.INSTANCE::toDTO)
      .collect(Collectors.toList());
  }

}
