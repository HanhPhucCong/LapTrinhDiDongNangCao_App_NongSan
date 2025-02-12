package org.agromarket.agro_server.model.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {

  @NotNull(message = "Star is required.")
  @DecimalMin(value = "1.0", inclusive = true, message = "Star rating must be at least {value}.")
  @DecimalMax(value = "5.0", inclusive = true, message = "Star rating must be at most {value}.")
  private Double star;

  @NotNull(message = "Comment is required.")
  @Size(min = 1, max = 500, message = "Comment must be between {min} and {max} characters.")
  private String comment;
}
