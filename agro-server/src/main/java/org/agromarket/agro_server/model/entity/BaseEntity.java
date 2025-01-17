package org.agromarket.agro_server.model.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
public abstract class BaseEntity {
  private boolean isActive; // trạng thái hoạt động
  private boolean isDeleted; // trạng thái xoá mềm
  @CreatedDate private LocalDateTime createdAt; // ngày tạo
  @LastModifiedDate private LocalDateTime updatedAt; // ngày cập nhật cuối cùng
}
