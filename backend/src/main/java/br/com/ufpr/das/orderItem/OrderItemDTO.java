package br.com.ufpr.das.orderItem;

import br.com.ufpr.das.product.ProductDTO;
import br.com.ufpr.das.purchaseOrder.PurchaseOrderDTO;
import lombok.Data;

@Data
public class OrderItemDTO {

  private Long id;
  private Integer quantity;
  private PurchaseOrderDTO order;
  private ProductDTO product;

}
