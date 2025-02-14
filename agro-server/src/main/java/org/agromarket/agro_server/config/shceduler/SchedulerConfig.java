package org.agromarket.agro_server.config.shceduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agromarket.agro_server.model.entity.LineItem;
import org.agromarket.agro_server.repositories.customer.LineItemRepository;
import org.agromarket.agro_server.repositories.customer.OrderRepository;
import org.agromarket.agro_server.repositories.customer.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

  private final OrderRepository orderRepository;
  private final LineItemRepository lineItemRepository;
  private final ProductRepository productRepository;

  @Bean
  public ScheduledExecutorService scheduledExecutorService() {
    return Executors.newScheduledThreadPool(5);
  }

  @Transactional
  @Scheduled(fixedRate = 60000) // chay moi 1 phut
  public void checkPaymentStatus() {
    LocalDateTime minutesAgo = LocalDateTime.now().minusMinutes(15);

    // lineItems pending > 15p
    List<LineItem> expiredLineItems = lineItemRepository.findExpiredLineItems(minutesAgo);

    if (!expiredLineItems.isEmpty()) {
      for (LineItem lineItem : expiredLineItems) {
        // reset product quantity
        productRepository.restoreStock(lineItem.getProduct().getId(), lineItem.getQuantity());
        lineItem.setOrder(null);
      }
      lineItemRepository.saveAll(expiredLineItems);
    }

    // cancel expired Orders
    int updatedOrders = orderRepository.markExpiredOrdersAsCanceled(minutesAgo);

    log.info(
        "Canceled {} expired orders and restored stock for {} line items",
        updatedOrders,
        expiredLineItems.size());
  }
}
