package org.agromarket.agro_server.controller.admin;
import org.agromarket.agro_server.model.dto.admin.OrderStatisticsDTO;
import org.agromarket.agro_server.model.dto.admin.ProductStatisticsDTO;
import org.agromarket.agro_server.service.admin.AdminStatisticalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/statistical")
public class AdminStatisticalController {
    private final AdminStatisticalService statisticalService;

    public AdminStatisticalController(AdminStatisticalService statisticalService) {
        this.statisticalService = statisticalService;
    }

    // API thống kê doanh thu
    @GetMapping("/revenue")
    public Double getTotalRevenue() {
        return statisticalService.getTotalRevenue();
    }

    // API thống kê đơn hàng
    @GetMapping("/orders")
    public OrderStatisticsDTO getOrderStatistics() {
        return statisticalService.getOrderStatistics();
    }

    // API thống kê top 10 sản phẩm bán chạy
    @GetMapping("/top-products")
    public List<ProductStatisticsDTO> getTopSellingProducts() {
        return statisticalService.getTopSellingProducts();
    }
}
