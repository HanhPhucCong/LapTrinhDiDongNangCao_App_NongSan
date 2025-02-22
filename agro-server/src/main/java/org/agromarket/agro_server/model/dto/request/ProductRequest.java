package org.agromarket.agro_server.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import org.agromarket.agro_server.common.Unit;

@Data
public class ProductRequest {

  @NotNull(message = "Category ID cannot be null")
  private Long categoryId;

  @NotBlank(message = "Product name cannot be empty")
  @Size(max = 100, message = "Product name must not exceed 100 characters")
  private String name;

  @NotBlank(message = "Description cannot be empty")
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;

  @NotNull(message = "Price cannot be null")
  @Positive(message = "Price must be greater than 0")
  private Double price;

  @NotNull(message = "Quantity cannot be null")
  @Positive(message = "Quantity must be greater than 0")
  private Long quantity;

  @NotNull(message = "Unit cannot be null")
  private Unit unit;

  private List<String> imageUrls; // List of image URLs instead of ProductImage objects
}
