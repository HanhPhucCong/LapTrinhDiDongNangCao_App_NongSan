package org.agromarket.agro_server.controller.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.model.dto.request.ReviewRequest;
import org.agromarket.agro_server.service.customer.ReviewService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PostMapping("/create/{productId}")
  public ResponseEntity<BaseResponse> createReview(
      @PathVariable("productId") long productId, @RequestBody @Valid ReviewRequest request) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Review Product successfully", 200, reviewService.createReview(productId, request)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @GetMapping("/get-all-active/{productId}")
  public ResponseEntity<BaseResponse> getAllActiveByProductId(
      @ParameterObject Pageable pageable, @PathVariable("productId") long productId) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Get all active review of Product successfully",
            200,
            reviewService.getAllReview(pageable, productId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @GetMapping("/{reviewId}")
  public ResponseEntity<BaseResponse> getActiveReviewById(@PathVariable("reviewId") long reviewId) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Get active Review by id successfully", 200, reviewService.getById(reviewId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/{reviewId}")
  public ResponseEntity<BaseResponse> updateById(
      @PathVariable("reviewId") long reviewId, @RequestBody @Valid ReviewRequest request) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Update Review successfully", 200, reviewService.update(reviewId, request)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PatchMapping("/softDelete/{reviewId}")
  public ResponseEntity<BaseResponse> softDelete(@PathVariable("reviewId") long reviewId) {
    boolean newIsDeleted = true;
    return ResponseEntity.ok(
        new BaseResponse(
            "Soft delete successfully",
            200,
            reviewService.toggleSoftDelete(newIsDeleted, reviewId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PatchMapping("/restore/{reviewId}")
  public ResponseEntity<BaseResponse> restoreDelete(@PathVariable("reviewId") long reviewId) {
    boolean newIsDeleted = false;
    return ResponseEntity.ok(
        new BaseResponse(
            "Restore successfully", 200, reviewService.toggleSoftDelete(newIsDeleted, reviewId)));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @DeleteMapping("/{reviewId}")
  public ResponseEntity<BaseResponse> forceDelete(@PathVariable("reviewId") long reviewId) {
    return ResponseEntity.ok(
        new BaseResponse(
            "Force delete review successfully", 200, reviewService.forceDelete(reviewId)));
  }
}
