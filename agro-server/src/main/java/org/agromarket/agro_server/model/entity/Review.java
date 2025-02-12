package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Review extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @DecimalMin(value = "1.0", inclusive = true, message = "Star rating must be at least {value}.")
  @DecimalMax(value = "5.0", inclusive = true, message = "Star rating must be at most {value}.")
  @JoinColumn(nullable = false)
  private Double rating;

  @Size(max = 255, message = "Comment must be less than or equal to 255 characters")
  @Column(length = 255)
  private String comment;
}
