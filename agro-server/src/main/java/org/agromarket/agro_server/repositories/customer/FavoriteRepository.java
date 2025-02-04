package org.agromarket.agro_server.repositories.customer;

import org.agromarket.agro_server.model.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
  @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId")
  Favorite getByUserId(long userId);
}
