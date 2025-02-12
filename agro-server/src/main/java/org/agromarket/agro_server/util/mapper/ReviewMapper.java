package org.agromarket.agro_server.util.mapper;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.model.dto.response.ReviewResponse;
import org.agromarket.agro_server.model.entity.Review;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {
  private final ModelMapper mapper;

  public ReviewResponse convertToResponse(Review review) {
    ReviewResponse response = mapper.map(review, ReviewResponse.class);
    response.setOwnerId(review.getUser().getId());
    response.setProductId(review.getProduct().getId());
    response.setStar(review.getRating());
    return response;
  }
}
