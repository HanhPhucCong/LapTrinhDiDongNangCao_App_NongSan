package org.agromarket.agro_server.service.customer.Impl;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.exception.NotFoundException;
import org.agromarket.agro_server.model.entity.User;
import org.agromarket.agro_server.repositories.customer.UserRepository;
import org.agromarket.agro_server.service.customer.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
      }
    };
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository
        .getUserByEmail(email)
        .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
  }
}
