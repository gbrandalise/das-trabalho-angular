package br.com.ufpr.das.purchaseOrder;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ufpr.das.client.ClientDTO;
import lombok.Data;

@Data
public class PurchaseOrderDTO {
  private Long id;

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private LocalDateTime date;
  @NotNull
  private ClientDTO client;
}
