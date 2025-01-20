package org.agromarket.agro_server.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SigninRequest {
  @NotBlank(message = "Vui lòng nhập email")
  @Email(message = "Vui lòng nhập đúng định dạng email")
  private String email;

  @NotBlank(message = "Vui lòng nhập mật khẩu")
  private String password;
}
