package org.agromarket.agro_server.service.admin;

import java.util.List;
import org.agromarket.agro_server.common.OrderStatus;
import org.agromarket.agro_server.model.entity.Order;
import org.agromarket.agro_server.repositories.admin.AdminOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminOrderService {

    @Autowired
    private AdminOrderRepository orderRepository;

    // Có thể dùng mapper để lấy các loại thành phần cần thiết nếu muốn
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}