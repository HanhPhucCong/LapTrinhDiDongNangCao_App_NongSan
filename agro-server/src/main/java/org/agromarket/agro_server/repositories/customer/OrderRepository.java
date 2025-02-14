package org.agromarket.agro_server.repositories.customer;

import java.time.LocalDateTime;
import java.util.List;
import org.agromarket.agro_server.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query(
      "SELECT o FROM Order o WHERE o.status = 'PENDING' AND o.createdAt < :time AND o.isDeleted=false")
  List<Order> expiredPendings(@Param("time") LocalDateTime time);

  @Modifying
  @Query(
      "UPDATE Order o SET o.status = 'CANCELED', o.isDeleted = true WHERE o.status = 'PENDING' AND o.createdAt < :time")
  int markExpiredOrdersAsCanceled(@Param("time") LocalDateTime time);
}
