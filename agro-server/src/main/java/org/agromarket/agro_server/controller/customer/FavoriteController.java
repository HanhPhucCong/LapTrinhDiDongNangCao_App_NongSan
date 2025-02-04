package org.agromarket.agro_server.controller.customer;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.service.customer.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {
  private final FavoriteService favoriteService;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/add/{productId}")
  public ResponseEntity<BaseResponse> addToFavorite(@PathVariable("productId") long productId) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Add product to favorite successfully!",
            200,
            favoriteService.addToFavorite(productId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/remove/{productId}")
  public ResponseEntity<BaseResponse> removeFromFavorite(
      @PathVariable("productId") long productId) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Remove product from favorite successfully!",
            200,
            favoriteService.removeFromFavorite(productId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @GetMapping("/my-favorite")
  public ResponseEntity<BaseResponse> myFavorites() {
    return ResponseEntity.ok(
        new BaseResponse("Get my favorites successfully!", 200, favoriteService.myFavorites()));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @DeleteMapping("/clear")
  public ResponseEntity<BaseResponse> clearFavorites() {
    return ResponseEntity.ok(
        new BaseResponse("Clear favorites successfully!", 200, favoriteService.clearFavorites()));
  }
}
