package org.agromarket.agro_server.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
  @NotNull(message = "Product in LineItem cannot be null")
  private Product product;

  @Column(nullable = false)
  @NotNull(message = "Quantity cannot be null")
  @Min(value = 0, message = "Quantity must be equal/greater than 0")
  @Max(value = 1000, message = "Quantity must be less than or equal to 1000")
  private int quantity;

  public double getPrice() {
    return this.product.getPrice() * this.quantity;
  }
}
