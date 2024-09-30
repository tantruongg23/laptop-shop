package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.repository.RoleRepository;

@Service
public class RoleService {
  private RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public List<Role> getAllRoles() {
    return this.roleRepository.findAll();
  }

  public String getRoleNameById(Long id) {
    return this.roleRepository.findRoleById(id).getName();

  }

  public Role getRoleByName(String roleName) {
    Role role = roleRepository.findByName(roleName);
    return role;
  }

}
