package org.agromarket.agro_server.service.customer;

import org.agromarket.agro_server.model.dto.response.CartResponse;

public interface CartService {

  // +1 (-1)
  public CartResponse adjustQuantity(long productId, int quantityChange);

  public CartResponse updateQuantity(long productId, int quantity);

  public CartResponse getCartByCurrenUser();

  // remove 1 item from Cart
  public CartResponse removeFromCart(long lineItemId);

  // clear all items
  public CartResponse clearCart();

  public Double totalPrice();
}
