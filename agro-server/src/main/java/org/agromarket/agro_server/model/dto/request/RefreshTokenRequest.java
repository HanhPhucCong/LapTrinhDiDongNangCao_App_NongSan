package org.agromarket.agro_server.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
  @NotBlank(message = "Token must be not null!")
  private String token;
}
