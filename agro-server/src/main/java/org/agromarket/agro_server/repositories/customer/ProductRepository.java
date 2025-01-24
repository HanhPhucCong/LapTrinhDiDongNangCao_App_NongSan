package org.agromarket.agro_server.repositories.customer;

import org.agromarket.agro_server.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("SELECT p FROM Product p WHERE p.id =:productId AND p.isActive=true AND p.isDeleted=false")
  Product getActiveProductById(@Param("productId") Long productId);
}
