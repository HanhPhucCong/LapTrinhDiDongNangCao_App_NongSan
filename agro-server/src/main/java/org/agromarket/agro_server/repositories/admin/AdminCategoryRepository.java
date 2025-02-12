package org.agromarket.agro_server.repositories.admin;

import org.agromarket.agro_server.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminCategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.isDeleted = false")
    List<Category> findAllActiveCategories();

    @Query("SELECT c FROM Category c WHERE c.isDeleted = true")
    List<Category> findAllDeletedCategories();
}
