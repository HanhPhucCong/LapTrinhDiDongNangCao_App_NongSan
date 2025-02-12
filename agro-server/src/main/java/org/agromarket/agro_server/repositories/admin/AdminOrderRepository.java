package org.agromarket.agro_server.repositories.admin;

import org.agromarket.agro_server.common.OrderStatus;
import org.agromarket.agro_server.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminOrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT COUNT(o) FROM Order o")
    Integer countTotalOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Integer countOrdersByStatus(OrderStatus status);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'DELIVERED'")
    Double calculateTotalRevenue();
}