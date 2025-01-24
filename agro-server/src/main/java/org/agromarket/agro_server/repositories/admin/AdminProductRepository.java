package org.agromarket.agro_server.repositories.admin;

import org.agromarket.agro_server.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdminProductRepository extends JpaRepository<Product, Long> {
}
