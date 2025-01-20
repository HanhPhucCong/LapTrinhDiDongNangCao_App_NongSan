package org.agromarket.agro_server.service.customer;

public interface MailService {
  public void sendMailVerify(
      String toEmail, String userName, String verifyCode, String templateFile);
}
