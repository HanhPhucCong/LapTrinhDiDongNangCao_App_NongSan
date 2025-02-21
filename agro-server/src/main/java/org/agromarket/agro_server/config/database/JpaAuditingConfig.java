package org.agromarket.agro_server.config.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
  // để dùng được tính năng tự động cập nhật các trường như ngày tạo, ngày cập nhật cuối,..
  // @CreatedDate
  // @LastModifiedDate
  // @CreatedBy
  // @LastModifiedBy
}
