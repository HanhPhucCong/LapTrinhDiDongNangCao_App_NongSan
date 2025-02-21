package org.agromarket.agro_server.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignoutRequest {
  @NotBlank(message = "Token is must be not null")
  private String token;

  @NotBlank(message = "RefreshToken is must be not null")
  private String refreshToken;
}
