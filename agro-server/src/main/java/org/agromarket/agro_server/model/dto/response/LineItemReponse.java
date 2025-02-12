package org.agromarket.agro_server.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LineItemReponse extends BaseResponseDTO {
  private long orderId;
  private long cartId;
  private long productId;
  private int quantity;
}
