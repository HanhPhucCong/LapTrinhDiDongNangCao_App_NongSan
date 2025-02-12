package org.agromarket.agro_server.model.dto.admin;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatisticsDTO {
    private Integer totalOrder;
    private Integer totalOrderPending;
    private Integer totalOrderProcessing;
    private Integer totalOrderShipping;
    private Integer totalOrderDelivered;
}
