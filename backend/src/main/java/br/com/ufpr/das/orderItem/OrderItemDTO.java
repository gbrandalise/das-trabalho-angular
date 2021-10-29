package br.com.ufpr.das.orderItem;

import javax.validation.constraints.NotNull;

import br.com.ufpr.das.product.ProductDTO;
import br.com.ufpr.das.purchaseOrder.PurchaseOrderDTO;
import lombok.Data;

@Data
public class OrderItemDTO {

  private Long id;
  @NotNull
  private Integer quantity;
  @NotNull
  private PurchaseOrderDTO order;
  @NotNull
  private ProductDTO product;

}
