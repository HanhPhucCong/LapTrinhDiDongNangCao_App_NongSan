package org.agromarket.agro_server.exception;

public class CustomException extends RuntimeException {
  private int statusCode;

  public CustomException(String msg, int statusCode) {
    super(msg);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String getMessage() {
    return super.getMessage() + " (status code: " + statusCode + ")";
  }
}
