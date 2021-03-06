package br.com.ufpr.das.purchaseOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import br.com.ufpr.das.client.ClientDTOFactory;
import br.com.ufpr.das.orderItem.OrderItemRepository;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseOrderServiceTest {

  @InjectMocks
  private PurchaseOrderService service;

  @Mock private PurchaseOrderRepository purchaseOrderRepository;
  @Mock private OrderItemRepository orderItemrepository;

  @Test
  public void testInstance() {
    assertNotNull(service);
  }

  @Test
  public void testInsert() {
    PurchaseOrderDTO order = PurchaseOrderDTOFactory.getOne("idNull");
    order.setClient(ClientDTOFactory.getOne("default"));
    PurchaseOrder orderEntity = PurchaseOrderFactory.getOne("default");
    when(purchaseOrderRepository.save(ArgumentMatchers.any())).thenReturn(orderEntity);
    PurchaseOrderDTO result = service.insert(order);
    verify(purchaseOrderRepository).save(ArgumentMatchers.any());

    assertNotNull(result);
    assertEquals(result.getId(), orderEntity.getId());
  }

  @Test(expected = ValidationException.class)
  public void testInsertIdNotNull() {
    PurchaseOrderDTO order = PurchaseOrderDTOFactory.getOne("default");
    service.insert(order);
  }

  @Test(expected = ValidationException.class)
  public void testInsertDateNull() {
    PurchaseOrderDTO order = PurchaseOrderDTOFactory.getOne("dateNull");
    service.insert(order);
  }

  @Test
    public void testFindAll() {
        List<PurchaseOrder> purchaseOrderEntities = PurchaseOrderFactory.getList(5, "default");
        when(purchaseOrderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(purchaseOrderEntities);
        List<PurchaseOrderDTO> result = service.findAll();
        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testFindAllEmptyList() {
        when(purchaseOrderRepository.findAll()).thenReturn(Collections.emptyList());
        List<PurchaseOrderDTO> result = service.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindById() {
        PurchaseOrder purchaseOrderEntity = PurchaseOrderFactory.getOne("default");
        when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(purchaseOrderEntity));
        PurchaseOrderDTO result = service.findById(1L);
        assertNotNull(result);
    }

    @Test(expected = ValidationException.class)
    public void testFindById_IdNull() {
        service.findById(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindByIdPurchaseOrderNotFound() {
        when(purchaseOrderRepository.findById(200L)).thenReturn(Optional.empty());
        service.findById(200L);
    }

  @Test
  public void testFindByClientCpf() {
    List<PurchaseOrder> purchaseOrderEntities = PurchaseOrderFactory.getList(5, "default");
    when(purchaseOrderRepository.findByClientCpf("99999999999", Sort.by(Sort.Direction.ASC, "id"))).thenReturn(purchaseOrderEntities);
    List<PurchaseOrderDTO> result = service.findByClientCpf("99999999999");
    assertNotNull(result);
    assertEquals(5, result.size());
  }

  @Test
  public void testFindByClientCpfEmptyList() {
    when(purchaseOrderRepository.findByClientCpf("99999999999", Sort.by(Sort.Direction.ASC, "id"))).thenReturn(Collections.emptyList());
    List<PurchaseOrderDTO> result = service.findByClientCpf("99999999999");
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

}
