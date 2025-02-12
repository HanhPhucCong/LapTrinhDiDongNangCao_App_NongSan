package org.agromarket.agro_server.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class BaseResponse {
  @JsonProperty("Message")
  private String message;

  @JsonProperty("Status")
  private int status;

  @JsonProperty("Data")
  private Object data;

  public BaseResponse(String message, int status, Object data) {
    this.message = message;
    this.status = status;
    this.data = data;
  }

  public String toJson() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }
}
