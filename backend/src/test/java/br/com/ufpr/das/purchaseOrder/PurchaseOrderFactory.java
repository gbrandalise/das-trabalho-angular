package br.com.ufpr.das.purchaseOrder;

import java.time.LocalDateTime;
import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class PurchaseOrderFactory {
  
  static {
    Fixture.of(PurchaseOrder.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("date", LocalDateTime.now());
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
