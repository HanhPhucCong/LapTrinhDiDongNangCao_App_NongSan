package org.agromarket.agro_server.controller.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.common.BaseResponse;
import org.agromarket.agro_server.model.dto.request.ProfileRequest;
import org.agromarket.agro_server.model.dto.response.CloudinaryResponse;
import org.agromarket.agro_server.service.customer.CloudinaryService;
import org.agromarket.agro_server.service.customer.UserService;
import org.agromarket.agro_server.util.file.FileUploadUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final CloudinaryService cloudinaryService;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @PutMapping("/update-profile")
  public ResponseEntity<BaseResponse> updateCurrentUser(
      @RequestPart("profileRequest") @Valid ProfileRequest profileRequest,
      @RequestPart(name = "file", required = false) MultipartFile file) {
    if (file != null && !file.isEmpty()) {
      FileUploadUtil.assertAllowedImage(file);
      final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
      final CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);

      profileRequest.setAvatarUrl(response.getUrl());
    }

    return userService.updateInfo(profileRequest);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
  @GetMapping("/my-profile")
  public ResponseEntity<BaseResponse> getCurrentProfile() {
    return userService.getCurrentProfile();
  }
}
