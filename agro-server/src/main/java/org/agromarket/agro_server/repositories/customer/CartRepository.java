package org.agromarket.agro_server.repositories.customer;

import org.agromarket.agro_server.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  public Cart findByUserId(long userId);
}
