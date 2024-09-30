package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User save(User user);

  Optional<User> findById(Long id);

  User findByEmail(String email);

  boolean existsByEmail(String email);

  void deleteById(Long id);

  Page<User> findAll(Pageable pageable);

  List<User> findOneByEmail(String email);
}
