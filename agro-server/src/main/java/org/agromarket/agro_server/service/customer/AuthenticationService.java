package org.agromarket.agro_server.service.customer;

import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.model.dto.request.*;
import org.agromarket.agro_server.model.dto.response.JwtAuthenticationResponse;
import org.agromarket.agro_server.model.dto.response.UserResponse;
import org.agromarket.agro_server.model.dto.response.VerifyResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
  public void autoCreateAdminAccount();

  public VerifyResponse signup(SignupRequest signupRequest);

  public UserResponse verifyUser(long userId, VerifyRequest verifyRequest);

  public JwtAuthenticationResponse signin(SigninRequest signinRequest);

  public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

  // gửi yêu cầu đổi/quên mk: gửi OTP
  public ResponseEntity<BaseResponse> sendVerifyRequest(
      ForgotPasswordRequest forgotPasswordRequest);

  // đổi mật khẩu
  public UserResponse renewPassword(long userId, RenewPasswordRequest renewPasswordRequest);
}
