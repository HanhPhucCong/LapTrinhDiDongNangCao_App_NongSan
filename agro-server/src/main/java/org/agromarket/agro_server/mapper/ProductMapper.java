package org.agromarket.agro_server.mapper;

import org.agromarket.agro_server.model.dto.admin.ProductDTO;
import org.agromarket.agro_server.model.entity.Product;

import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getUnit().toString(),
                product.getCategory().getName(),
                product.getImages()
                        .stream()
                        .map(image -> image.getUrl()) // Lấy URL ảnh
                        .collect(Collectors.toList()),
                product.getIsActive(),
                product.getIsDeleted(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
