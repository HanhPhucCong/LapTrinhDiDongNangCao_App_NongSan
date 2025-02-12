package org.agromarket.agro_server.config.vnpay;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class VNPayConfig {

  @Value("${vnpay.pay-url}")
  private String payUrl;

  @Value("${vnpay.return-url}")
  private String returnUrl;

  @Value("${vnpay.tmn-code}")
  private String tmnCode;

  @Value("${vnpay.secret-key}")
  private String secretKey;

  @Value("${vnpay.api-url}")
  private String apiUrl;

  @Value("${vnpay.version}")
  private String version;

  @Value("${vnpay.command}")
  private String command;

  @Value("${vnpay.order-type}")
  private String orderType;
}
