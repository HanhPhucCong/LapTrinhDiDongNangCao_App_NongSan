package org.agromarket.agro_server.service.customer.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agromarket.agro_server.model.dto.request.ProductRequest;
import org.agromarket.agro_server.model.dto.response.ProductResponse;
import org.agromarket.agro_server.model.entity.Product;
import org.agromarket.agro_server.repositories.customer.ProductRepository;
import org.agromarket.agro_server.service.customer.ProductService;
import org.agromarket.agro_server.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Transactional
  @Override
  public ProductResponse create(ProductRequest productRequest) {
    Product product = productMapper.convertToEntity(productRequest);
    return productMapper.convertToReponse(productRepository.save(product));
  }

  @Override
  public Page<ProductResponse> getAllActive(Pageable pageable) {
    Page<Product> products = productRepository.getAllByIsActiveTrueAndIsDeletedFalse(pageable);
    return products.map(productMapper::convertToReponse);
  }
}
