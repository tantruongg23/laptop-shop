package vn.hoidanit.laptopshop.controller.client;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.RoleService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class ClientUserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  public ClientUserController(UserService userService,
      PasswordEncoder passwordEncoder,
      RoleService roleService) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
  }

  @GetMapping("/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("registerUser", new RegisterDTO());
    return "client/auth/register";
  }

  @PostMapping("/register")
  public String getRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerUser,
      BindingResult registerBindingResult) {

    if (registerBindingResult.hasErrors()) {
      return "client/auth/register";
    }

    User user = this.userService.registerDtoToUser(registerUser);
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    user.setRole(this.roleService.getRoleByName("USER"));

    this.userService.handleSaveUser(user);
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String getLoginPage(Model model) {

    return "client/auth/login";
  }

  @GetMapping("/access-deny")
  public String getDenyPage(Model model) {

    return "client/auth/deny";
  }
}
