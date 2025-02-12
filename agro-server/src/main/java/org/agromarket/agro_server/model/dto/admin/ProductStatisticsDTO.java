package org.agromarket.agro_server.model.dto.admin;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStatisticsDTO {
    private Long productId;
    private String name;
    private Double totalPrice;
    private Long totalQuantity;
    private String category;
}
