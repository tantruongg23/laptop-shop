package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class DashBoardController {

  private final UserService userService;
  private final ProductService productService;
  private final OrderService orderService;

  public DashBoardController(UserService userService, ProductService productService, OrderService orderService) {
    this.userService = userService;
    this.productService = productService;
    this.orderService = orderService;
  }

  @GetMapping("/admin")
  public String getDashBoard(Model model) {

    Long numberOfUsers = this.userService.countUsers();
    Long numberOfProducts = this.productService.countProducts();
    Long numberOfOrders = this.orderService.countOrders();
    model.addAttribute("numberOfUsers", numberOfUsers);
    model.addAttribute("numberOfProducts", numberOfProducts);
    model.addAttribute("numberOfOrders", numberOfOrders);
    return "admin/dashboard/show";
  }

}
