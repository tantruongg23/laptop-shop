package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
    this.orderRepository = orderRepository;
    this.orderDetailRepository = orderDetailRepository;
  }

  public Page<Order> fetchOrders(Pageable pageable) {
    return this.orderRepository.findAll(pageable);
  }

  public List<OrderDetail> getOrderDetailById(Long id) {
    List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrderId(id);
    return orderDetails != null && orderDetails.size() > 0 ? orderDetails : null;
  }

  public Order getById(Long id) {
    Optional<Order> order = this.orderRepository.findById(id);
    return order.isPresent() == true ? order.get() : null;
  }

  public void handleUpdateOrder(Order order) {
    Optional<Order> currentOrder = this.orderRepository.findById(order.getId());
    if (currentOrder.isPresent()) {
      Order newOrder = currentOrder.get();
      newOrder.setStatus(order.getStatus());
      this.orderRepository.save(newOrder);
    }

  }

  public void deleteAOrder(Long id) {
    // delete order detail
    List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrderId(id);

    for (OrderDetail od : orderDetails) {
      this.orderDetailRepository.delete(od);
    }

    this.orderRepository.deleteById(id);
  }

  public Long countOrders() {
    return this.orderRepository.count();
  }

  public List<Order> fetchOrderByUser(User user) {
    return this.orderRepository.findByUser(user);
  }

}
