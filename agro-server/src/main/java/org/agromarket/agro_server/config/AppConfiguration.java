package org.agromarket.agro_server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AppConfiguration {
  @Value("5") // 5 phút
  private Integer verifyExpireTime;

  @Value("10")
  private Integer logRounds;
}
