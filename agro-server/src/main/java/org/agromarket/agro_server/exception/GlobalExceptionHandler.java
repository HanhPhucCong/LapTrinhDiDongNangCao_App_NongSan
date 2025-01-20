package org.agromarket.agro_server.exception;

import lombok.extern.slf4j.Slf4j;
import org.agromarket.agro_server.common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException e) {
    log.error("Not found exception: ", e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
        .body(new BaseResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), ""));
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<BaseResponse> handleCustomException(CustomException e) {
    log.error("Custom exception: ", e);
    return ResponseEntity.status(e.getStatusCode())
        .body(new BaseResponse(e.getMessage(), e.getStatusCode(), ""));
  }
}
