package org.agromarket.agro_server.repositories.admin;

import org.agromarket.agro_server.model.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDiscountRepository extends JpaRepository<Discount, Long> {
}