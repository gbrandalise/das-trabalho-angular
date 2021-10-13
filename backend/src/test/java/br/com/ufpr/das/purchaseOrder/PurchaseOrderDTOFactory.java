package br.com.ufpr.das.purchaseOrder;

import java.time.LocalDate;
import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.ufpr.das.client.ClientFactory;

public class PurchaseOrderDTOFactory {
  static {
    Fixture.of(PurchaseOrderDTO.class).addTemplate("default", new Rule() {
      {
        add("id", random(Long.class, range(1L, 200L)));
        add("date", LocalDate.now());
        add("client", ClientFactory.getOne("default"));
      }
    });
    Fixture.of(PurchaseOrderDTO.class).addTemplate("idNull").inherits("default", new Rule() {
      {
        add("id", null);
      }
    });
    Fixture.of(PurchaseOrderDTO.class).addTemplate("dateNull").inherits("idNull", new Rule() {
      {
        add("date", null);
      }
    });
  }

  public static PurchaseOrderDTO getOne(String label) {
    return Fixture.from(PurchaseOrderDTO.class).gimme(label);
  }

  public static List<PurchaseOrderDTO> getList(int quantity, String label) {
    return Fixture.from(PurchaseOrderDTO.class).gimme(quantity, label);
  }
}
