package br.com.ufpr.das.orderItem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.ufpr.das.order.Order;
import br.com.ufpr.das.product.Product;
import lombok.Data;

@Data
@Entity(name = "order_items")
public class OrderItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer quantity;

  @ManyToOne
  private Order order;

  @ManyToOne
  private Product product;
}
