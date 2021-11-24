package br.com.ufpr.das.orderItem;

import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.ufpr.das.product.ProductDTOFactory;
import br.com.ufpr.das.purchaseOrder.PurchaseOrderDTOFactory;

public class OrderItemDTOFactory {
  static {
    Fixture.of(OrderItemDTO.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("quantity", random(Integer.class, range(1, 3000)));
      }
    });
    Fixture.of(OrderItemDTO.class).addTemplate("valid")
      .inherits("default", new Rule() {
        {
          add("product", ProductDTOFactory.getOne("default"));
          add("order", PurchaseOrderDTOFactory.getOne("default"));
        }
      });
    Fixture.of(OrderItemDTO.class).addTemplate("idNull")
      .inherits("valid", new Rule() {
        {
          add("id", null);
        }
      });
    Fixture.of(OrderItemDTO.class).addTemplate("quantityNull")
      .inherits("default", new Rule() {
        {
          add("quantity", null);
        }
      });
  }

  public static OrderItemDTO getOne(String label) {
    return Fixture.from(OrderItemDTO.class).gimme(label);
  }

  public static List<OrderItemDTO> getList(int quantity, String label) {
    return Fixture.from(OrderItemDTO.class).gimme(quantity, label);
  }
}
