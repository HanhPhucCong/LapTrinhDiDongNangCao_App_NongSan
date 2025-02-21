package org.agromarket.agro_server.repositories.customer;

import org.agromarket.agro_server.model.entity.BlackListRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRefreshTokenRepository
    extends JpaRepository<BlackListRefreshToken, Long> {

  @Query("SELECT b FROM BlackListRefreshToken b WHERE b.refreshToken = :refreshToken")
  public BlackListRefreshToken getByToken(String refreshToken);
}
