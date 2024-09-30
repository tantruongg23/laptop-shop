package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User handleSaveUser(User user) {
    return this.userRepository.save(user);
  }

  public Page<User> fetchUsers(Pageable pageable) {
    return this.userRepository.findAll(pageable);
  }

  public List<User> getAllUsersByEmail(String email) {
    return this.userRepository.findOneByEmail(email);
  }

  public Optional<User> getUserDetailById(Long id) {
    return this.userRepository.findById(id);
  }

  public void deleteAUser(Long id) {
    this.userRepository.deleteById(id);
  }

  public User registerDtoToUser(RegisterDTO registerDTO) {
    User user = new User();
    user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
    user.setEmail(registerDTO.getEmail());
    user.setPassword(registerDTO.getPassword());
    return user;
  }

  public boolean checkEmailExist(String email) {
    return this.userRepository.existsByEmail(email);
  }

  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public Long countUsers() {
    return this.userRepository.count();
  }
}
