package org.agromarket.agro_server.service.customer.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agromarket.agro_server.exception.NotFoundException;
import org.agromarket.agro_server.model.dto.response.OrderResponse;
import org.agromarket.agro_server.model.entity.Order;
import org.agromarket.agro_server.model.entity.User;
import org.agromarket.agro_server.repositories.customer.OrderRepository;
import org.agromarket.agro_server.service.customer.OrderService;
import org.agromarket.agro_server.util.mapper.OrderMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  @Override
  public List<OrderResponse> myOrders() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();

    return orderRepository.getConfirmedByUserId(user.getId()).stream()
        .map(orderMapper::convertToResponse)
        .toList();
  }

  @Override
  public OrderResponse getById(long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new NotFoundException("Order cannot found with id: " + orderId));
    return orderMapper.convertToResponse(order);
  }

  @Override
  public List<OrderResponse> getAllStatus_NotDelete() {
    return orderRepository.getAllNotDeleted().stream().map(orderMapper::convertToResponse).toList();
  }
}
