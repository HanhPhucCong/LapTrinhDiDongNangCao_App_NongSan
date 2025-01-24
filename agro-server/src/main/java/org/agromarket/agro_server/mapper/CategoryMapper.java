package org.agromarket.agro_server.mapper;

import org.agromarket.agro_server.model.dto.admin.CategoryDTO;
import org.agromarket.agro_server.model.entity.Category;

import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getIsActive(),
                category.getIsDeleted(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
