package org.agromarket.agro_server.util.mapper;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.model.dto.response.LineItemReponse;
import org.agromarket.agro_server.model.entity.LineItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineItemMapper {
  private final ModelMapper mapper;

  public LineItemReponse convertToResponse(LineItem lineItem) {
    LineItemReponse response = mapper.map(lineItem, LineItemReponse.class);
    if (lineItem.getCart() != null) {
      response.setCartId(lineItem.getCart().getId());
    }
    if (lineItem.getOrder() != null) {
      response.setOrderId(lineItem.getOrder().getId());
    }

    response.setProductId(lineItem.getProduct().getId());
    return response;
  }
}
