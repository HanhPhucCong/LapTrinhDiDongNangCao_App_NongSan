package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "black_list_refresh_token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
// Bảng chứa những refresh token bị vô hiệu hoá
public class BlackListRefreshToken extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String refreshToken;

  @Column(nullable = false)
  private boolean isRevoked;

  public BlackListRefreshToken(String refreshToken, boolean isRevoked) {
    this.refreshToken = refreshToken;
    this.isRevoked = isRevoked;
  }
}
