package org.agromarket.agro_server.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequest {
  private String lineItemIds;
  private String shippingAddress;
  private String note;
}

/*
* {
  "lineItemIds": [1,3,5],
  "shippingAddress": "123 Đường ABC, TP.HCM",
  "note": "Giao hàng trong giờ hành chính"
}
* */
