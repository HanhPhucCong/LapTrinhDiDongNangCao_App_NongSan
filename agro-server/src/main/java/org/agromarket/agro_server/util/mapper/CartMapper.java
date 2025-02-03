package org.agromarket.agro_server.util.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.model.dto.response.CartResponse;
import org.agromarket.agro_server.model.dto.response.LineItemReponse;
import org.agromarket.agro_server.model.entity.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {
  private final ModelMapper mapper;
  private final LineItemMapper lineItemMapper;

  public CartResponse convertToResponse(Cart cart) {
    List<LineItemReponse> itemReponseList = cart.getLineItems()
            .stream().map(lineItemMapper::convertToResponse)
            .toList();

    CartResponse response = mapper.map(cart, CartResponse.class);
    response.setUserId(cart.getUser().getId());
    response.setLineItems(itemReponseList);
    return response;
  }
}
