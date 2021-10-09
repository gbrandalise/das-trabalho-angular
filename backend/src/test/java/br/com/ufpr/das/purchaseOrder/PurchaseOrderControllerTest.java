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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseOrderControllerTest {

  @InjectMocks
  private PurchaseOrderController controller;

  @Mock
  private PurchaseOrderService purchaseOrderService;

  @Test
    public void testInstance() {
        assertNotNull(controller);
    }

  @Test
  public void testFindAll() {
      List<PurchaseOrderDTO> purchaseORdersDto = PurchaseOrderDTOFactory.getList(5, "default");
      when(purchaseOrderService.findAll()).thenReturn(purchaseORdersDto);
      ResponseEntity<List<PurchaseOrderDTO>> result = controller.findAll();
      verify(purchaseOrderService).findAll();
      assertNotNull(result.getBody());
      assertEquals(5, result.getBody().size());
  }

  @Test
  public void testFindAllEmptyList() {
      when(purchaseOrderService.findAll()).thenReturn(Collections.emptyList());
      ResponseEntity<List<PurchaseOrderDTO>> result = controller.findAll();
      verify(purchaseOrderService).findAll();
      assertNotNull(result.getBody());
      assertTrue(result.getBody().isEmpty());
  }
}
