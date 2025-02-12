package org.agromarket.agro_server.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  private Boolean isActive = true;
  private Boolean isDeleted = false;
  @CreatedDate private LocalDateTime createdAt; // ngày tạo
  @LastModifiedDate private LocalDateTime updatedAt; // ngày cập nhật cuối cùng
}
