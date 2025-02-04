package org.agromarket.agro_server.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.agromarket.agro_server.common.Unit;
import org.agromarket.agro_server.model.entity.ProductImage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductReponse extends BaseResponseDTO {
  private long categoryId;
  private String name;
  private String description;
  private Double price;
  private List<ProductImage> images;
  private long quantity;
  private Unit unit;
}
