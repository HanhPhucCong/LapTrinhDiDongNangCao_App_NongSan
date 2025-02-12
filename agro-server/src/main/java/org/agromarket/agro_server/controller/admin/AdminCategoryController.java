package org.agromarket.agro_server.controller.admin;

import org.agromarket.agro_server.model.dto.admin.CategoryDTO;
import org.agromarket.agro_server.model.entity.Category;
import org.agromarket.agro_server.service.admin.AdminCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(adminCategoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(adminCategoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
        return ResponseEntity.ok("Category with ID " + id + " has been marked as deleted.");
    }

    @PatchMapping("/restore/{id}")
    public ResponseEntity<String> restoreCategory(@PathVariable Long id) {
        adminCategoryService.restoreCategory(id);
        return ResponseEntity.ok("Category with ID " + id + " has been restored.");
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(adminCategoryService.getAllCategories());
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<CategoryDTO>> getAllDeletedCategories() {
        return ResponseEntity.ok(adminCategoryService.getAllDeletedCategories());
    }
}
