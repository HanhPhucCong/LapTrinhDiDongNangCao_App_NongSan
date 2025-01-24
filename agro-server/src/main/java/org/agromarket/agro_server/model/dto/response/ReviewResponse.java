package org.agromarket.agro_server.model.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ReviewResponse extends BaseResponseDTO {

  private Long ownerId;
  private Long productId;
  private Double star;
  private String comment;
}
