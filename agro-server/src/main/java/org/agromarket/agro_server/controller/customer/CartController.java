package org.agromarket.agro_server.controller.customer;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.service.customer.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/increment/{productId}")
  public ResponseEntity<BaseResponse> increment(@PathVariable("productId") long productId) {
    // +1
    int quantityChange = 1;
    return ResponseEntity.ok(
        new BaseResponse(
            "Add to cart successfully!",
            200,
            cartService.adjustQuantity(productId, quantityChange)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/decrement/{productId}")
  public ResponseEntity<BaseResponse> decrement(@PathVariable("productId") long productId) {
    // -1
    int quantityChange = -1;
    return ResponseEntity.ok(
        new BaseResponse(
            "Reduce from cart successfully!",
            200,
            cartService.adjustQuantity(productId, quantityChange)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/update/{productId}/{quantity}")
  public ResponseEntity<BaseResponse> updateQuantity(
      @PathVariable("productId") long productId, @PathVariable("quantity") int quantity) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Update quantity for cart successfully!",
            200,
            cartService.updateQuantity(productId, quantity)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @GetMapping("/my-cart")
  public ResponseEntity<BaseResponse> myCart() {
    return ResponseEntity.ok(
        new BaseResponse("Get my cart successfully!", 200, cartService.getCartByCurrenUser()));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/remove/{lineItemId}")
  public ResponseEntity<BaseResponse> removeFromCart(@PathVariable("lineItemId") long lineItemId) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Remove from cart successfully!", 200, cartService.removeFromCart(lineItemId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @DeleteMapping("/clear")
  public ResponseEntity<BaseResponse> clearCart() {
    return ResponseEntity.ok(
        new BaseResponse("Clear cart successfully!", 200, cartService.clearCart()));
  }
}
