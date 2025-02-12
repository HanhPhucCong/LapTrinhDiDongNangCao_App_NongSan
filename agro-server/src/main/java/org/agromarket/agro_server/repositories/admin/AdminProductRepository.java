package org.agromarket.agro_server.repositories.admin;

import org.agromarket.agro_server.model.dto.admin.ProductStatisticsDTO;
import org.agromarket.agro_server.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdminProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        SELECT new org.agromarket.agro_server.model.dto.admin.ProductStatisticsDTO(
            p.id, p.name, SUM(li.quantity * p.price), SUM(li.quantity), p.category.name
        )
        FROM LineItem li
        JOIN li.product p
        JOIN li.order o
        WHERE o.status = 'DELIVERED'
        GROUP BY p.id, p.name, p.category.name
        ORDER BY SUM(li.quantity) DESC
    """)
    List<ProductStatisticsDTO> findTopSellingProducts();
}
