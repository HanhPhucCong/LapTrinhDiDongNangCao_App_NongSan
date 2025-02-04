package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Favorite extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  @ManyToMany
  @JoinTable(
      name = "favorite_product",
      joinColumns = @JoinColumn(name = "favorite_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products = new ArrayList<>();
}
