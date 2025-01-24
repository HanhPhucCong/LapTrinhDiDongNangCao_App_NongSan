package org.agromarket.agro_server.model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private long quantity;
    private String unit;
    private String category;
    private List<String> imageUrls;
    private Boolean isActive;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}