package org.agromarket.agro_server.repositories.customer;

import org.agromarket.agro_server.model.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {
  @Transactional
  @Modifying
  @Query("DELETE FROM LineItem li WHERE li.cart.id = :cartId")
  void deleteAllByCartId(@Param("cartId") long cartId);
}
