package org.agromarket.agro_server.util.mapper;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.model.dto.response.UserResponse;
import org.agromarket.agro_server.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final ModelMapper modelMapper;

  public UserResponse convertToResponse(User user) {
    return modelMapper.map(user, UserResponse.class);
  }
}
