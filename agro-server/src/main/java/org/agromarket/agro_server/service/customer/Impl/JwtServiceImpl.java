package org.agromarket.agro_server.service.customer.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.agromarket.agro_server.service.customer.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
  @Override
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 phut
        .signWith(getSigninKey(), Jwts.SIG.HS256)
        .compact();
  }

  @Override
  public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
    return Jwts.builder()
        .claims(extractClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 ngay
        .signWith(getSigninKey(), Jwts.SIG.HS256)
        .compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
  }

  private SecretKey getSigninKey() {
    byte[] key =
        Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
    return Keys.hmacShaKeyFor(key);
  }
}
