package br.com.ufpr.das.order;

import java.time.LocalDate;
import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class OrderFaqctory {
  static {
    Fixture.of(Order.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("date", LocalDate.now());
      }
    });
  }

  public static Order getOne(String label) {
    return Fixture.from(Order.class).gimme(label);
  }

  public static List<Order> getList(int quantity, String label) {
    return Fixture.from(Order.class).gimme(quantity, label);
  }

}
