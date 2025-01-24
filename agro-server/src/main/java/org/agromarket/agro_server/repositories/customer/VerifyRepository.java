package org.agromarket.agro_server.repositories.customer;

import org.agromarket.agro_server.model.entity.Verify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyRepository extends JpaRepository<Verify, Long> {}
