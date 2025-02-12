package org.agromarket.agro_server.service.customer;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUserName(String token);

  String generateToken(UserDetails userDetails);

  String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails);

  boolean isTokenValid(String token, UserDetails userDetails);
}
