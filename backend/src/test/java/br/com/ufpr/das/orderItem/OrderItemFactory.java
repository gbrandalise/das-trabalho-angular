package br.com.ufpr.das.orderItem;

import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class OrderItemFactory {
  static {
    Fixture.of(OrderItem.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("quantity", random(Integer.class, range(1, 3000)));
      }
    });
  }

  public static OrderItem getOne(String label) {
    return Fixture.from(OrderItem.class).gimme(label);
  }

  public static List<OrderItem> getList(int quantity, String label) {
    return Fixture.from(OrderItem.class).gimme(quantity, label);
  }
}
