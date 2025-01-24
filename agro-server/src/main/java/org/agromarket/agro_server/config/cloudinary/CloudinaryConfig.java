package org.agromarket.agro_server.config.cloudinary;

import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.config.AppConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {
  private final AppConfiguration appConfig;

  @Bean
  public Cloudinary cloudinary() {
    final Map<String, String> config = new HashMap<>();
    config.put("cloud_name", appConfig.getCloudName());
    config.put("api_key", appConfig.getCloudApiKey());
    config.put("api_secret", appConfig.getCloudApiSecret());
    return new Cloudinary(config);
  }
}
