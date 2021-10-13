package br.com.ufpr.das.purchaseOrder;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

import br.com.ufpr.das.client.Client;
import lombok.Data;

@Data
public class PurchaseOrderDTO {
  private Long id;

  @NotNull
  private LocalDate date;
  @NotNull
  private Client client;
}
