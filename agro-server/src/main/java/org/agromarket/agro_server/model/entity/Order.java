package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.agromarket.agro_server.common.OrderStatus;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @NotNull(message = "Owner of Order cannot be null")
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @NotNull(message = "List lineItems cannot be null")
  private List<LineItem> lineItems;

  @Column(nullable = false, length = 255)
  @Size(max = 255, message = "Shipping address must be less than or equal to 255 characters")
  @NotNull(message = "Shipping address cannot be null")
  private String shippingAddress;

  @Column(nullable = false)
  @Min(value = 0, message = "Total amount can not be less than 0!")
  private double totalAmount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @NotNull(message = "OrderStatus cannot be null")
  private OrderStatus status;
}
