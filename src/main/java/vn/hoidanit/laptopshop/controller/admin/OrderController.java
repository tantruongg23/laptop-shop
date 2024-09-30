package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/admin/order")
  public String getOrder(Model model,
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
    Pageable pageable = PageRequest.of(page - 1, 5);

    // Get data, set data to model
    Page<Order> pageOrders = this.orderService.fetchOrders(pageable);
    List<Order> orders = pageOrders.getContent();
    model.addAttribute("orders", orders);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", pageOrders.getTotalPages());

    // Return page default if page param is false
    if (isPageFalse == true) {
      return "redirect:/admin/order?page=" + page;
    }

    return "admin/order/show";
  }

  @GetMapping("/admin/order/{id}")
  public String getOrderDetail(@PathVariable Long id, Model model) {
    List<OrderDetail> orderDetails = this.orderService.getOrderDetailById(id);
    model.addAttribute("orderDetails", orderDetails);

    return "admin/order/show-detail";
  }

  @GetMapping("/admin/order/update/{id}")
  public String updateOrderPage(@PathVariable Long id, Model model) {
    Order order = this.orderService.getById(id);
    model.addAttribute("order", order);
    return "admin/order/update";
  }

  @PostMapping("/admin/order/update")
  public String updateOrder(Model model,
      @ModelAttribute("order") Order order) {

    this.orderService.handleUpdateOrder(order);

    return "redirect:/admin/order"; // Sửa URL redirect
  }

  @GetMapping("/admin/order/delete/{id}")
  public String deleteOrderPage(@PathVariable Long id, Model model) {
    Order order = new Order();
    order.setId(id);
    model.addAttribute("newOrder", order);
    return "admin/order/delete";
  }

  @PostMapping("/admin/order/delete")
  public String deleteOrder(@ModelAttribute("newOrder") Order tantr) {
    this.orderService.deleteAOrder(tantr.getId());
    return "redirect:/admin/order";
  }

}
