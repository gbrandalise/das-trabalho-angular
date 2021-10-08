package br.com.ufpr.das.order;

import java.time.LocalDate;
import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class OrderDTOFactory {
  static {
    Fixture.of(OrderDTO.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("date", LocalDate.now());
      }
    });
    Fixture.of(OrderDTO.class).addTemplate("idNull").inherits("default", new Rule() {
      {
        add("id", null);
      }
    });
    Fixture.of(OrderDTO.class).addTemplate("dateNull").inherits("idNull", new Rule() {
      {
        add("date", null);
      }
    });
  }

  public static OrderDTO getOne(String label) {
    return Fixture.from(OrderDTO.class).gimme(label);
  }

  public static List<OrderDTO> getList(int quantity, String label) {
    return Fixture.from(OrderDTO.class).gimme(quantity, label);
  }
}
