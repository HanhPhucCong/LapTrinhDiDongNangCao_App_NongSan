package org.agromarket.agro_server.repositories.customer;

import org.agromarket.agro_server.model.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {

  @Query("SELECT b FROM BlackListToken b WHERE b.token = :token")
  public BlackListToken getByToken(String token);
}
