package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "black_list_token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
// Bảng chứa những token bị vô hiệu hoá
public class BlackListToken extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private boolean isRevoked;

  public BlackListToken(String token, boolean isRevoked) {
    this.token = token;
    this.isRevoked = isRevoked;
  }
}
