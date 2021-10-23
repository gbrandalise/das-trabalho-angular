package br.com.ufpr.das.orderItem;

import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class OrderItemDTOFactory {
  static {
    Fixture.of(OrderItemDTO.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("quantity", random(Integer.class, range(1, 3000)));
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
