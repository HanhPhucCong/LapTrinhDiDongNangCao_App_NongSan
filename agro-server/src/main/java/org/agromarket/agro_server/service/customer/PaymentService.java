package org.agromarket.agro_server.service.customer;

import jakarta.servlet.http.HttpServletRequest;
import org.agromarket.agro_server.model.dto.request.CheckoutRequest;
import org.agromarket.agro_server.model.dto.response.PaymentResponse;

public interface PaymentService {
  public PaymentResponse createPayment(HttpServletRequest request, CheckoutRequest checkoutRequest);

  public double getAmountFromListLineItem(String listLineItems);

  public void handlePaymentSuccess(String orderInfo);
}
