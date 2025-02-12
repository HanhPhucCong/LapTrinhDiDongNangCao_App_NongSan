package org.agromarket.agro_server.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.common.ErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {

    String message = "Bạn không có quyền truy cập vào tài nguyên này.";
    String errorCode = ErrorCode.FORBIDDEN.name();

    BaseResponse baseResponse =
        new BaseResponse(message, HttpServletResponse.SC_FORBIDDEN, errorCode);

    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getWriter().write(baseResponse.toJson());
  }
}
