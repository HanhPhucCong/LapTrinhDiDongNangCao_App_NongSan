package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
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

  @JoinColumn(nullable = false)
  private Double rating;

  @Column(length = 255)
  private String comment;
}
