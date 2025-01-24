package org.agromarket.agro_server.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
  @NotBlank(message = "Vui lòng nhập email.")
  @Email(message = "Vui lòng nhập đúng định dạng email.")
  private String email;
}
