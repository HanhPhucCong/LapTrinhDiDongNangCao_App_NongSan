package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.agromarket.agro_server.common.Unit;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  @NotNull(message = "Product name cannot be null")
  private String name;

  @Column(nullable = false)
  @NotNull(message = "Product description cannot be null")
  private String description;

  @Column(nullable = false)
  @NotNull(message = "Product price cannot be null")
  @Min(value = 0, message = "Price must be at least 0")
  @Max(value = 100000000, message = "Price must be less than or equal to 100,000,000")
  private Double price = 0.0;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductImage> images = new ArrayList<>();

  @Column(nullable = false)
  @NotNull(message = "Product quantity cannot be null")
  @Min(value = 0, message = "Quantity must be zero or greater")
  @Max(value = 100000, message = "Quantity must be less than or equal to 100000")
  private long quantity;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @NotNull(message = "Product unit cannot be null")
  private Unit unit;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  @NotNull(message = "Product category cannot be null")
  private Category category;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  public void addImage(ProductImage image) {
    images.add(image);
    image.setProduct(this);
  }

  public void clearImages() {
    for (ProductImage image : images) {
      image.setProduct(null);
    }
    images.clear();
  }
}