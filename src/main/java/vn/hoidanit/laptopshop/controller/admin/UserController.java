package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.RoleService;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

  private final UserService userService;
  private final RoleService roleService;
  private final UploadService uploadService;
  private final PasswordEncoder passwordEncoder;

  public UserController(
      UserService userService,
      RoleService roleService, UploadService uploadService,
      PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.roleService = roleService;
    this.uploadService = uploadService;
    this.passwordEncoder = passwordEncoder;
  }

  @RequestMapping("/")
  public String getHomePage() {
    return "hello";
  }

  @GetMapping("/admin/user")
  public String getListUsers(Model model,
      @RequestParam("page") Optional<String> pageOptional) {
    // Handle page param
    int page = 1;
    boolean isPageFalse = false;
    try {
      page = Integer.parseInt(pageOptional.get());
    } catch (Exception e) {
      isPageFalse = true;
    }

    // Set page, limit
    Pageable pageable = PageRequest.of(page - 1, 3);

    // Get data, set data to model
    Page<User> pageUsers = this.userService.fetchUsers(pageable);
    List<User> users = pageUsers.getContent();
    model.addAttribute("users", users);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", pageUsers.getTotalPages());

    // Return page default if page param is false
    if (isPageFalse == true) {
      return "redirect:/admin/user?page=" + page;
    }

    return "admin/user/show";
  }

  @GetMapping("/admin/user/create")
  public String getCreateUserPage(Model model) {
    List<Role> roles = roleService.getAllRoles();
    model.addAttribute("newUser", new User());
    model.addAttribute("roles", roles);
    return "admin/user/create";
  }

  @PostMapping("/admin/user/create")
  public String createUserPage(Model model,
      @ModelAttribute("newUser") @Valid User tantr,
      BindingResult newUserBindingResult,
      @RequestParam("tantrFile") MultipartFile file) {

    // validate
    if (newUserBindingResult.hasErrors()) {
      List<Role> roles = roleService.getAllRoles();
      model.addAttribute("roles", roles);
      return "/admin/user/create";
    }

    String avatar = this.uploadService.handleUploadFile(file, "avatar");
    tantr.setAvatar(avatar);

    String hashedPassword = this.passwordEncoder.encode(tantr.getPassword());
    tantr.setPassword(hashedPassword);

    this.userService.handleSaveUser(tantr);

    return "redirect:/admin/user";
  }

  @GetMapping("/admin/user/{id}")
  public String getUserDetail(@PathVariable Long id, Model model) {
    Optional<User> user = this.userService.getUserDetailById(id);
    model.addAttribute("user", user);

    return "admin/user/user-detail";
  }

  @GetMapping("/admin/user/update/{id}")
  public String updateUserPage(@PathVariable Long id, Model model) {
    Optional<User> currentUser = this.userService.getUserDetailById(id);
    model.addAttribute("newUser", currentUser);

    return "admin/user/update";
  }

  @PostMapping("/admin/user/update")
  public String updateUser(Model model,
      @ModelAttribute("newUser") User tantr,
      BindingResult newUserBindingResult) {

    // validate
    if (newUserBindingResult.hasErrors()) {
      List<Role> roles = roleService.getAllRoles();
      model.addAttribute("roles", roles);
      return "/admin/user/create";
    }

    try {
      User currentUser = this.userService.getUserDetailById(tantr.getId()).get();
      if (currentUser != null) {
        currentUser.setFullName(tantr.getFullName());
        currentUser.setAddress(tantr.getAddress());
        currentUser.setPhone(tantr.getPhone());

        this.userService.handleSaveUser(currentUser);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/admin/user"; // Sửa URL redirect
  }

  @GetMapping("/admin/user/delete/{id}")
  public String deleteUserPage(@PathVariable Long id, Model model) {
    User user = new User();
    user.setId(id);
    model.addAttribute("newUser", user);
    return "admin/user/delete";
  }

  @PostMapping("/admin/user/delete")
  public String deleteUser(@ModelAttribute("newUser") User tantr) {
    this.userService.deleteAUser(tantr.getId());
    return "redirect:/admin/user";
  }

}
