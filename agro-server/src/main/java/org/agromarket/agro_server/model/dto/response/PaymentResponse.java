package org.agromarket.agro_server.model.dto.response;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse implements Serializable {
  private String status;
  private String message;
  private String URL;
}
