package org.agromarket.agro_server.controller.admin;
import org.agromarket.agro_server.model.dto.admin.CategoryDTO;
import org.agromarket.agro_server.model.dto.admin.InforUserDTO;
import org.agromarket.agro_server.service.admin.AdminUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been marked as deleted.");
    }
    @PatchMapping("/restore/{id}")
    public ResponseEntity<String> restoreCategory(@PathVariable Long id) {
        adminUserService.restoreUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been restored.");
    }
    @GetMapping
    public ResponseEntity<List<InforUserDTO>> getAllCategories() {
        return ResponseEntity.ok(adminUserService.getAllUser());
    }
    @GetMapping("/{id}")
    public ResponseEntity<InforUserDTO> getAllCategories(@PathVariable Long id) {
        return ResponseEntity.ok(adminUserService.getUser(id));
    }
}
