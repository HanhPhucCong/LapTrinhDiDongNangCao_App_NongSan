package org.agromarket.agro_server.util.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.model.dto.response.FavoriteRespone;
import org.agromarket.agro_server.model.dto.response.ProductReponse;
import org.agromarket.agro_server.model.entity.Favorite;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteMapper {
  private final ModelMapper mapper;
  private final ProductMapper productMapper;

  public FavoriteRespone convertToResponse(Favorite favorite) {
    List<ProductReponse> productDTOList =
        favorite.getProducts().stream().map(productMapper::convertToReponse).toList();
    FavoriteRespone respone = mapper.map(favorite, FavoriteRespone.class);
    respone.setUserId(favorite.getUser().getId());
    respone.setProducts(productDTOList);
    return respone;
  }
}
