package org.agromarket.agro_server.service.customer;

import java.io.IOException;
import org.agromarket.agro_server.exception.CustomException;
import org.agromarket.agro_server.model.dto.response.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
  public CloudinaryResponse uploadFile(MultipartFile file, String fileName) throws CustomException;

  public CloudinaryResponse uploadVideo(MultipartFile file, String fileName) throws CustomException;

  public boolean deleteFileById(String publicId) throws CustomException;

  public void deleteByUrl(String url) throws IOException;
}
