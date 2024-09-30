package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

  private final ProductService productService;

  public CartController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/cart")
  public String getCartPage(Model model, HttpServletRequest request) {
    User currentUser = new User();// null
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    Cart cart = this.productService.fetchByUser(currentUser);

    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

    double totalPrice = 0;
    for (CartDetail cd : cartDetails) {
      totalPrice += cd.getPrice() * cd.getQuantity();
    }

    model.addAttribute("cartDetails", cartDetails);
    model.addAttribute("totalPrice", totalPrice);

    model.addAttribute("cart", cart);

    return "client/cart/show";

  }

  @PostMapping("/delete-cart-product/{cartDetailId}")
  public String postMethodName(@PathVariable Long cartDetailId, HttpServletRequest request) {
    // TODO: process POST request
    HttpSession session = request.getSession(false);
    // xoa cartDetail
    this.productService.handleDeleteCartDetail(cartDetailId, session);

    return "redirect:/cart";
  }

  @GetMapping("/checkout")
  public String getCheckoutPage(Model model, HttpServletRequest request) {
    User currentUser = new User();// null
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    Cart cart = this.productService.fetchByUser(currentUser);

    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

    double totalPrice = 0;
    for (CartDetail cd : cartDetails) {
      totalPrice += cd.getPrice() * cd.getQuantity();
    }

    model.addAttribute("cartDetails", cartDetails);
    model.addAttribute("totalPrice", totalPrice);

    model.addAttribute("cart", cart);

    return "client/cart/checkout";

  }

  @PostMapping("/confirm-checkout")
  public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
    this.productService.handleUpdateCartBeforeCheckout(cartDetails);
    return "redirect:/checkout";
  }

  @PostMapping("/place-order")
  public String getCheckOut(@ModelAttribute("cart") Cart cart,
      @RequestParam("receiverName") String receiverName,
      @RequestParam("receiverAddress") String receiverAddress,
      @RequestParam("receiverPhone") String receiverPhone,
      HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    User currentUser = new User();

    Long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
    return "client/cart/thanks";
  }

  @GetMapping("/thanks")
  public String getMethodName() {
    return "client/cart/thanks";
  }

}
