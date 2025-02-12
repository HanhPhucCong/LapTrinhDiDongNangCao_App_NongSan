package org.agromarket.agro_server.service.admin;

import org.agromarket.agro_server.model.dto.admin.OrderStatisticsDTO;
import org.agromarket.agro_server.model.dto.admin.ProductStatisticsDTO;
import org.agromarket.agro_server.repositories.admin.AdminOrderRepository;
import org.agromarket.agro_server.repositories.admin.AdminProductRepository;
import org.agromarket.agro_server.common.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStatisticalService {
    private final AdminProductRepository productRepository;
    private final AdminOrderRepository orderRepository;

    public AdminStatisticalService(AdminProductRepository productRepository, AdminOrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // Thống kê doanh thu
    public Double getTotalRevenue() {
        return orderRepository.calculateTotalRevenue();
    }

    // Thống kê đơn hàng theo trạng thái
    public OrderStatisticsDTO getOrderStatistics() {
        return new OrderStatisticsDTO(
                orderRepository.countTotalOrders(),
                orderRepository.countOrdersByStatus(OrderStatus.PENDING),
                orderRepository.countOrdersByStatus(OrderStatus.PROCESSING),
                orderRepository.countOrdersByStatus(OrderStatus.SHIPPING),
                orderRepository.countOrdersByStatus(OrderStatus.DELIVERED)
        );
    }

    // Lấy top 10 sản phẩm bán chạy nhất
    public List<ProductStatisticsDTO> getTopSellingProducts() {
        return productRepository.findTopSellingProducts();
    }
}
