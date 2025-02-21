package org.agromarket.agro_server.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "line_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LineItem extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  // nếu chưa thanh toán thì cột này có giá trị, cho order_id null
  @ManyToOne
  @JoinColumn(name = "cart_id")
  @JsonIgnore
  private Cart cart;

  // nếu đã thanh toán thì cột này có giá trị, cho cart_id null
  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
  private Order order;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private int quantity;

  public double getPrice() {
    return this.product.getPrice() * this.quantity;
  }
}
