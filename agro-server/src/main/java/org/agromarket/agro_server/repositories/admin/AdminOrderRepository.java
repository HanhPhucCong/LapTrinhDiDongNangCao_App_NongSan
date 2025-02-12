package org.agromarket.agro_server.repositories.admin;

import org.agromarket.agro_server.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminOrderRepository extends JpaRepository<Order, Long> {
}