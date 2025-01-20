package org.agromarket.agro_server.model.dto.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
  private String token;
  private String refreshToken;
}
