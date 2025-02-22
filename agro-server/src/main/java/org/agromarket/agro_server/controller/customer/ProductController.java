package org.agromarket.agro_server.controller.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.model.dto.request.ProductRequest;
import org.agromarket.agro_server.model.dto.response.ProductResponse;
import org.agromarket.agro_server.service.customer.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @PostMapping("/products/create")
  public ResponseEntity<BaseResponse> createProduct(@RequestBody @Valid ProductRequest request) {
    return ResponseEntity.ok(
        new BaseResponse("Create product successfully!", 200, productService.create(request)));
  }

  @GetMapping("/public/products/all-active")
  public ResponseEntity<BaseResponse> getAllProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {

    Pageable pageable =
        PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
    Page<ProductResponse> products = productService.getAllActive(pageable);

    return ResponseEntity.ok(new BaseResponse("Get all active successfully!", 200, products));
  }
}
