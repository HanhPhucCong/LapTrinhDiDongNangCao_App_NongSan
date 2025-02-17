package org.agromarket.agro_server.service.customer;

import java.util.List;
import org.agromarket.agro_server.model.dto.response.OrderResponse;

public interface OrderService {
  public List<OrderResponse> myOrders();

  public OrderResponse getById(long orderId);

  public List<OrderResponse> getAllStatus_NotDelete();
}
