package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class HomePageController {

  private final ProductService productService;
  private final OrderService orderService;

  public HomePageController(ProductService productService,
      OrderService orderService) {
    this.productService = productService;
    this.orderService = orderService;
  }

  @GetMapping("/")
  public String getHomePage(Model model) {
    // page = , limit =
    Pageable pageable = PageRequest.of(0, 4);

    Page<Product> pageProducts = this.productService.fetchProducts(pageable);
    List<Product> products = pageProducts.getContent();
    model.addAttribute("products", products);
    return "client/homepage/show";
  }

  @GetMapping("/order-history")
  public String getOrderHistoryPage(Model model, HttpServletRequest request) {
    User currentUser = new User();// null
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
    model.addAttribute("orders", orders);

    return "client/cart/order-history";
  }

}
