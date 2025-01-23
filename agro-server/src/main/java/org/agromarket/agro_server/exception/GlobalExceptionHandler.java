package org.agromarket.agro_server.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.agromarket.agro_server.common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    String errorMessage = "Invalid or missing request body. Please check your input.";
    log.error("HttpMessageNotReadableException: ", ex);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
        .body(new BaseResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), ""));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
  }

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
