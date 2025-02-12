package org.agromarket.agro_server.service.customer;

import java.util.List;
import org.agromarket.agro_server.model.dto.request.ReviewRequest;
import org.agromarket.agro_server.model.dto.response.ReviewResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
  public ReviewResponse createReview(Long productId, ReviewRequest request);

  public List<ReviewResponse> getAllReview(Pageable pageable, Long productId);

  public ReviewResponse getById(long reviewId);

  public ReviewResponse update(long reviewId, ReviewRequest request);

  public ReviewResponse toggleSoftDelete(boolean flag, long reviewId);

  public ReviewResponse forceDelete(long reviewId);
}
