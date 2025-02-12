package org.agromarket.agro_server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AppConfiguration {

  // OTP
  @Value("5") // 5 phút
  private Integer verifyExpireTime;

  @Value("10")
  private Integer logRounds;

  // cloudinary
  @Value("${cloud_name}")
  private String cloudName;

  @Value("${cloud_api_key}")
  private String cloudApiKey;

  @Value("${cloud_api_secret}")
  private String cloudApiSecret;
}
