package org.agromarket.agro_server.repositories.customer;

import java.util.List;
import org.agromarket.agro_server.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  @Query(
      "SELECT r FROM Review r WHERE r.user.id = :userId AND r.isActive = true AND r.isDeleted = false")
  public List<Review> getAllByUser(@Param("userId") Long userId);

  @Query(
      "SELECT r FROM Review r WHERE r.product.id = :productId AND r.isActive = true AND r.isDeleted = false")
  Page<Review> getActiveReviewsByProductId(@Param("productId") Long productId, Pageable pageable);

  Review getByIdAndIsActiveTrueAndIsDeletedFalse(long reviewId);
}
