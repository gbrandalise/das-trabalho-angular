package br.com.ufpr.das.order;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {
  private Long id;

  @NotNull
  private LocalDate date;
}
