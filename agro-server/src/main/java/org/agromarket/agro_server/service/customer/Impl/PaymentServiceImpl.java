package org.agromarket.agro_server.service.customer.Impl;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agromarket.agro_server.config.vnpay.VNPayConfig;
import org.agromarket.agro_server.model.dto.request.PaymentRequest;
import org.agromarket.agro_server.model.dto.response.PaymentResponse;
import org.agromarket.agro_server.service.customer.PaymentService;
import org.agromarket.agro_server.util.vnpay.VNPayUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  private final VNPayConfig vnpayConfig;

  @Override
  public PaymentResponse createPayment(HttpServletRequest request, PaymentRequest paymentRequest) {

    String vnp_TxnRef = VNPayUtil.getRandomNumber(8);
    String vnp_IpAddr = VNPayUtil.getIpAddress(request);

    Map<String, String> vnp_Params = buildParams(vnp_TxnRef, vnp_IpAddr, paymentRequest);

    String paymentUrl = buildPaymentUrl(vnp_Params);

    PaymentResponse paymentResponse = new PaymentResponse();
    paymentResponse.setStatus("Ok");
    paymentResponse.setMessage("Successfully");
    paymentResponse.setURL(paymentUrl);

    return paymentResponse;
  }

  private Map<String, String> buildParams(
      String vnp_TxnRef, String vnp_IpAddr, PaymentRequest paymentRequest) {
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnpayConfig.getVersion());
    vnp_Params.put("vnp_Command", vnpayConfig.getCommand());
    vnp_Params.put("vnp_TmnCode", vnpayConfig.getTmnCode());

    long amount = (long) (paymentRequest.getAmount() * 100L);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));

    vnp_Params.put("vnp_CurrCode", "VND");
    vnp_Params.put("vnp_BankCode", "NCB");
    vnp_Params.put("vnp_OrderType", vnpayConfig.getOrderType());
    vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());

    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);

    vnp_Params.put(
        "vnp_OrderInfo", formatOrderInfo(paymentRequest.getUserId(), paymentRequest.getAmount()));

    vnp_Params.put("vnp_Locale", "vn");
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    System.out.println(vnp_Params);

    return vnp_Params;
  }

  private String formatOrderInfo(long userId, Double amount) {
    // amount có phần thập phân .0, loại bỏ nó
    String amountStr = String.format("%.0f", amount);
    return userId + "." + amountStr;
  }

  private String buildPaymentUrl(Map<String, String> vnp_Params) {
    List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
    Collections.sort(fieldNames);

    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();

    for (String fieldName : fieldNames) {
      String fieldValue = vnp_Params.get(fieldName);
      if (fieldValue != null && !fieldValue.isEmpty()) {
        // Build hash data
        hashData.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

        // Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

        query.append('&');
        hashData.append('&');
      }
    }

    if (!query.isEmpty()) query.setLength(query.length() - 1);
    if (!hashData.isEmpty()) hashData.setLength(hashData.length() - 1);

    String vnp_SecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData.toString());
    return vnpayConfig.getPayUrl() + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;
  }
}
