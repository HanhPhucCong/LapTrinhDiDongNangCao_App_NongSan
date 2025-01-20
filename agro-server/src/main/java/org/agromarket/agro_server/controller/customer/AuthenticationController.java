package org.agromarket.agro_server.controller.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.model.dto.request.RefreshTokenRequest;
import org.agromarket.agro_server.model.dto.request.SigninRequest;
import org.agromarket.agro_server.model.dto.request.SignupRequest;
import org.agromarket.agro_server.model.dto.request.VerifyRequest;
import org.agromarket.agro_server.model.dto.response.JwtAuthenticationResponse;
import org.agromarket.agro_server.service.customer.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  // sign up step 1
  @PostMapping("/signup")
  public ResponseEntity<BaseResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new BaseResponse(
                "Mã xác thực đã được gửi đến email: " + signupRequest.getEmail(),
                HttpStatus.OK.value(),
                authenticationService.signup(signupRequest)));
  }

  // sign up step 2
  @PostMapping("/signup/{userId}")
  public ResponseEntity<BaseResponse> signupStep2(
      @PathVariable("userId") long userId, @Valid @RequestBody VerifyRequest verifyRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new BaseResponse(
                "Đăng ký tài khoản thành công!",
                HttpStatus.OK.value(),
                authenticationService.verifyUser(userId, verifyRequest)));
  }

  @PostMapping("/signin")
  public ResponseEntity<JwtAuthenticationResponse> signin(
      @RequestBody SigninRequest signinRequest) {
    return ResponseEntity.ok(authenticationService.signin(signinRequest));
  }

  @PostMapping("/refresh")
  public ResponseEntity<JwtAuthenticationResponse> refresh(
      @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
  }
}
