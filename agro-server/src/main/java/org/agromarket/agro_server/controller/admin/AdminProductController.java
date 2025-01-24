package org.agromarket.agro_server.controller.admin;

import org.agromarket.agro_server.model.dto.admin.ProductDTO;
import org.agromarket.agro_server.model.entity.Product;
import org.agromarket.agro_server.service.admin.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final AdminProductService productService;

    @Autowired
    public AdminProductController(AdminProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }
    @PatchMapping("/restore/{id}")
    public ResponseEntity<String> restoreCategory(@PathVariable Long id) {
        productService.restoreProduct(id);
        return ResponseEntity.ok("Product with ID " + id + " has been restored.");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/add-quantity")
    public ResponseEntity<Product> addProductQuantity(@PathVariable Long id, @RequestParam int addedQuantity) {
        Product updatedProduct = productService.addProductQuantity(id, addedQuantity);
        return ResponseEntity.ok(updatedProduct);
    }
}