package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role save(Role role);

  Role findRoleById(Long id);

  Role findByName(String roleName);

  void deleteById(Long id);

  List<Role> findAll();

}
