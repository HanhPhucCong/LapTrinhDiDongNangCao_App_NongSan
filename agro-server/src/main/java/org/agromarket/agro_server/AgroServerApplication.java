package org.agromarket.agro_server;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.service.customer.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AgroServerApplication implements CommandLineRunner {
  private final AuthenticationService authenticationService;

  public static void main(String[] args) {
    SpringApplication.run(AgroServerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    authenticationService.autoCreateAdminAccount();
  }
}
