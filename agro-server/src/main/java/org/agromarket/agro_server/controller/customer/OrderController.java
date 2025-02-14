package org.agromarket.agro_server.controller.customer;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.service.customer.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @GetMapping("/my-orders")
  public ResponseEntity<BaseResponse> myOrders() {
    return ResponseEntity.ok(
        new BaseResponse("Get my orders successfully!", 200, orderService.myOrders()));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @GetMapping("/{orderId}")
  public ResponseEntity<BaseResponse> getById(@PathVariable("orderId") long orderId) {
    return ResponseEntity.ok(
        new BaseResponse("Get order by id successfully!", 200, orderService.getById(orderId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @GetMapping("/all-status")
  public ResponseEntity<BaseResponse> getAllNotDeleted() {
    return ResponseEntity.ok(
        new BaseResponse(
            "Get all order (not deleted) successfully!",
            200,
            orderService.getAllStatus_NotDelete()));
  }
}
