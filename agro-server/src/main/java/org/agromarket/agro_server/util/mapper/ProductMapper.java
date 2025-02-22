package org.agromarket.agro_server.util.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.exception.NotFoundException;
import org.agromarket.agro_server.model.dto.request.ProductRequest;
import org.agromarket.agro_server.model.dto.response.ProductResponse;
import org.agromarket.agro_server.model.entity.*;
import org.agromarket.agro_server.repositories.customer.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
  private final ModelMapper mapper;
  private final CategoryRepository categoryRepository;

  public ProductResponse convertToReponse(Product product) {

    Long categoryId = product.getCategory().getId();
    List<String> imageUrls = product.getImages().stream().map(ProductImage::getUrl).toList();
    List<Long> reviewIds = product.getReviews().stream().map(Review::getId).toList();
    List<Long> favoriteIds = product.getFavorites().stream().map(Favorite::getId).toList();

    ProductResponse response = mapper.map(product, ProductResponse.class);
    response.setCategoryId(categoryId);
    response.setImageUrls(imageUrls);
    response.setReviewIds(reviewIds);
    response.setFavoriteIds(favoriteIds);
    return response;
  }

  public Product convertToEntity(ProductRequest request) {
    Category category =
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Category not found with ID: " + request.getCategoryId()));

    Product product = mapper.map(request, Product.class);
    product.setCategory(category);

    if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
      List<ProductImage> images =
          request.getImageUrls().stream()
              .map(
                  url -> {
                    ProductImage image = new ProductImage();
                    image.setUrl(url);
                    image.setProduct(product);
                    return image;
                  })
              .toList();
      product.setImages(images);
    }

    return product;
  }
}
