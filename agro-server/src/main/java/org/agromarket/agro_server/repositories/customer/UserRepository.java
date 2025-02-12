package org.agromarket.agro_server.repositories.customer;

import java.util.List;
import java.util.Optional;
import org.agromarket.agro_server.common.Role;
import org.agromarket.agro_server.model.entity.User;
import org.agromarket.agro_server.model.entity.Verify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByEmail(String email);

  public User findByRole(Role role);

  public boolean existsByEmail(String email);

  @Query("SELECT v FROM Verify v WHERE v.user.id = :userId AND v.isRevoked = true")
  public List<Verify> getAllRevokedVerify(long userId);

  public Optional<User> getUserByEmail(String email);
}
