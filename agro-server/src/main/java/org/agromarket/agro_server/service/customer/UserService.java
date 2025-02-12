package org.agromarket.agro_server.service.customer;

import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.model.dto.request.ProfileRequest;
import org.agromarket.agro_server.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
  public UserDetailsService userDetailsService();

  public User getUserByEmail(String email);

  public ResponseEntity<BaseResponse> updateInfo(ProfileRequest profileRequest);

  public ResponseEntity<BaseResponse> getCurrentProfile();
}
