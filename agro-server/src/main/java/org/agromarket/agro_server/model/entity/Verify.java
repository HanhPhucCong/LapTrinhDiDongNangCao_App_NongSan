package org.agromarket.agro_server.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "verifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Verify extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;
  private LocalDateTime expireAt; // thời gian hết hạn

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  private Boolean isRevoked = false;
}
