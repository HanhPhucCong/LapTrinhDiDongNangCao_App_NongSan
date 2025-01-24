package org.agromarket.agro_server.service.customer;

import org.agromarket.agro_server.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
  public UserDetailsService userDetailsService();

  public User getUserByEmail(String email);
}
