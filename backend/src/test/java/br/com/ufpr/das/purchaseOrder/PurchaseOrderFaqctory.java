package br.com.ufpr.das.purchaseOrder;

import java.time.LocalDate;
import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class PurchaseOrderFaqctory {
  static {
    Fixture.of(PurchaseOrder.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("date", LocalDate.now());
        // add("client", );
      }
    });
  }

  public static PurchaseOrder getOne(String label) {
    return Fixture.from(PurchaseOrder.class).gimme(label);
  }

  public static List<PurchaseOrder> getList(int quantity, String label) {
    return Fixture.from(PurchaseOrder.class).gimme(quantity, label);
  }

}
