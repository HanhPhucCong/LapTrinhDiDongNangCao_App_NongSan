package org.agromarket.agro_server.exception;

import lombok.extern.slf4j.Slf4j;
import org.agromarket.agro_server.common.BaseResponse;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<BaseResponse> handleNullPointerException(NullPointerException ex) {
    log.error("NullPointerException occurred: ", ex);

    String errorMessage =
        "Có vẻ như một số dữ liệu bị thiếu hoặc không hợp lệ. Vui lòng kiểm tra lại và thử lại.";

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new BaseResponse(
                errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(), "NULL_POINTER_EXCEPTION"));
  }

  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public ResponseEntity<BaseResponse> handleInvalidDataAccess(
      InvalidDataAccessApiUsageException ex) {
    log.error("Invalid Data Access: ", ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new BaseResponse(
                "Dữ liệu không hợp lệ. Vui lòng kiểm tra thông tin gửi lên.",
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_DATA_ACCESS"));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("IllegalArgumentException: ", ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new BaseResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    String errorMessage = "Invalid or missing request body. Please check your input.";
    log.error("HttpMessageNotReadableException: ", ex);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
        .body(new BaseResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), ""));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    String errorMessage =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getDefaultMessage()) // Lấy thông báo lỗi
            .findFirst() // Chỉ lấy thông báo lỗi đầu tiên
            .orElse("Validation failed. Please check the fields.");

    BaseResponse response = new BaseResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), null);
    return ResponseEntity.badRequest().body(response);
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
