package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<LineItem> lineItems = new ArrayList<>();

  @Override
  public int hashCode() {
    return Objects.hash(id); // Chỉ dùng id để tránh vòng lặp
  }
}
