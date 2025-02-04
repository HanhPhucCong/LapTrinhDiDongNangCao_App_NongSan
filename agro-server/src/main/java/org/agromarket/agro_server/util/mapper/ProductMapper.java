package org.agromarket.agro_server.util.mapper;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.model.dto.response.ProductReponse;
import org.agromarket.agro_server.model.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
  private final ModelMapper mapper;

  public ProductReponse convertToReponse(Product product) {
    ProductReponse response = mapper.map(product, ProductReponse.class);
    response.setCategoryId(product.getCategory().getId());
    return response;
  }
}
