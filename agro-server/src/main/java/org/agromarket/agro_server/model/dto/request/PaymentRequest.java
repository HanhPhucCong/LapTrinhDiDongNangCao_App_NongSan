package org.agromarket.agro_server.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

  @NotNull(message = "User ID cannot be null")
  private Long userId;

  @NotNull(message = "Amount cannot be null")
  @Positive(message = "Amount must be a positive number")
  @Min(value = 0, message = "Amount must be at least 0")
  private Double amount;
}
