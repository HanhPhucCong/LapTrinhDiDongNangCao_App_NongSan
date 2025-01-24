package org.agromarket.agro_server.service.admin;

import org.agromarket.agro_server.model.dto.admin.ProductDTO;
import org.agromarket.agro_server.mapper.ProductMapper;
import org.agromarket.agro_server.model.entity.Category;
import org.agromarket.agro_server.model.entity.Product;
import org.agromarket.agro_server.model.entity.ProductImage;
import org.agromarket.agro_server.repositories.admin.AdminProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminProductService {

    private final AdminProductRepository productRepository;

    @Autowired
    public AdminProductService(AdminProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return ProductMapper.toProductDTO(product);
    }

    public Product createProduct(Product product) {
        if (product.getImages() != null) {
            product.getImages().forEach(image -> image.setProduct(product));
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setUnit(product.getUnit());
        existingProduct.setCategory(product.getCategory());

        List<ProductImage> newImages = product.getImages();
        existingProduct.getImages().clear();
        newImages.forEach(image -> {
            image.setProduct(existingProduct);
            existingProduct.getImages().add(image);
        });

        return productRepository.save(existingProduct);
    }
    public void restoreProduct(Long id) {
        Product restoreProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        if (Boolean.TRUE.equals(restoreProduct.getIsDeleted())) {
            restoreProduct.setIsDeleted(false);
            restoreProduct.setIsActive(true);
            productRepository.save(restoreProduct);
        } else {
            throw new IllegalStateException("Product with ID " + id + " is not deleted.");
        }
    }
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.setIsDeleted(true);
        product.setIsActive(false);
        productRepository.save(product);
    }
    public Product addProductQuantity(Long id, int addedQuantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        if (addedQuantity <= 0) {
            throw new IllegalArgumentException("Added quantity must be greater than 0.");
        }
        product.setQuantity(product.getQuantity() + addedQuantity);
        return productRepository.save(product);
    }
}
