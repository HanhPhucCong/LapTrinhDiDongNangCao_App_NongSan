package org.agromarket.agro_server.service.customer;

import org.agromarket.agro_server.model.dto.request.RefreshTokenRequest;
import org.agromarket.agro_server.model.dto.request.SigninRequest;
import org.agromarket.agro_server.model.dto.request.SignupRequest;
import org.agromarket.agro_server.model.dto.request.VerifyRequest;
import org.agromarket.agro_server.model.dto.response.JwtAuthenticationResponse;
import org.agromarket.agro_server.model.dto.response.UserResponse;
import org.agromarket.agro_server.model.dto.response.VerifyResponse;

public interface AuthenticationService {
  public void autoCreateAdminAccount();

  public VerifyResponse signup(SignupRequest signupRequest);

  public UserResponse verifyUser(long userId, VerifyRequest verifyRequest);

  public JwtAuthenticationResponse signin(SigninRequest signinRequest);

  public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
