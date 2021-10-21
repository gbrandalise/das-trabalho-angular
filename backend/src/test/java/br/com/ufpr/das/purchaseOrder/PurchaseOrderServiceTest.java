package br.com.ufpr.das.purchaseOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseOrderServiceTest {

  @InjectMocks
  private PurchaseOrderService service;

  @Mock
  private PurchaseOrderRepository purchaseOrderRepository;

  @Test
  public void testInstance() {
    assertNotNull(service);
  }

  @Test
  public void testInsert() {
    PurchaseOrderDTO purchaseOrder = PurchaseOrderDTOFactory.getOne("idNull");
    PurchaseOrder orderEntity = PurchaseOrderFaqctory.getOne("default");
    when(purchaseOrderRepository.save(ArgumentMatchers.any())).thenReturn(orderEntity);
    PurchaseOrderDTO result = service.insert(purchaseOrder);
    verify(purchaseOrderRepository).save(ArgumentMatchers.any());

    assertNotNull(result);
    assertEquals(result.getId(), orderEntity.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInsertIdNotNull() {
    PurchaseOrderDTO order = PurchaseOrderDTOFactory.getOne("default");
    service.insert(order);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInsertDateNull() {
    PurchaseOrderDTO order = PurchaseOrderDTOFactory.getOne("dateNull");
    service.insert(order);
  }

  @Test
    public void testFindAll() {
        List<PurchaseOrder> purchaseOrderEntities = PurchaseOrderFaqctory.getList(5, "default");
        when(purchaseOrderRepository.findAll()).thenReturn(purchaseOrderEntities);
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

}
