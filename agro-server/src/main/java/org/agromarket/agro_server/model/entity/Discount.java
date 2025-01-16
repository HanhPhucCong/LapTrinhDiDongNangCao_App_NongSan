package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Discount extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(max = 10, message = "Code must be less than or equal to 10 characters")
  @NotNull(message = "Discount code cannot be null")
  @Column(length = 10, nullable = false)
  private String code;

  @DecimalMin(value = "0.0", message = "Discount percentage must be between 0 and 100")
  @DecimalMax(value = "100.0", message = "Discount percentage must be between 0 and 100")
  @NotNull(message = "Discount percentage cannot be null")
  private double discountPercentage;

  @NotNull(message = "Start date cannot be null")
  @FutureOrPresent(message = "Start date must be in the present or future")
  private LocalDateTime startDate;

  @NotNull(message = "End date cannot be null")
  @Future(message = "End date must be in the future")
  private LocalDateTime endDate;
}
