package vn.hoidanit.laptopshop.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.specification.ProductSpecs;
import vn.hoidanit.laptopshop.utils.NotNullUtil;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final CartDetailRepository cartDetailRepository;
  private final UserService userService;
  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  public ProductService(
      ProductRepository productRepository,
      CartRepository cartRepository,
      CartDetailRepository cartDetailRepository,
      UserService userService,
      OrderRepository orderRepository,
      OrderDetailRepository orderDetailRepository) {
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
    this.cartDetailRepository = cartDetailRepository;
    this.userService = userService;
    this.orderDetailRepository = orderDetailRepository;
    this.orderRepository = orderRepository;
  }

  public Product handleSaveProduct(Product product) {
    return this.productRepository.save(product);
  }

  public Page<Product> fetchProductsByName(Pageable pageable, String name) {
    return this.productRepository.findAll(ProductSpecs.nameLike(name), pageable);
  }

  public Page<Product> fetchProductsByMinPrice(Pageable pageable, double price) {
    return this.productRepository.findAll(ProductSpecs.minPrice(price), pageable);
  }

  public Page<Product> fetchProductsByMaxPrice(Pageable pageable, double price) {
    return this.productRepository.findAll(ProductSpecs.maxPrice(price), pageable);
  }

  public Page<Product> fetchProductsByFactory(Pageable pageable, String factory) {
    List<String> facs = Arrays.asList(factory.split(","));
    return this.productRepository.findAll(ProductSpecs.matchListFactory(facs), pageable);
  }

  public Specification<Product> buildPriceSpecification(List<String> prices) {

    Specification<Product> combinedSpec = Specification.where(null);

    for (String price : prices) {
      double min = 1;
      double max = Double.MAX_VALUE;
      switch (price) {
        case "duoi-10-trieu":
          max = 10000000;
          break;

        case "tu-10-15-trieu":
          min = 10000000;
          max = 15000000;
          break;

        case "tu-15-20-trieu":
          min = 15000000;
          max = 20000000;
          break;

        case "tren-20-trieu":
          min = 20000000;
          break;

        default:
          max = 0;
          break;
      }

      Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min, max);
      combinedSpec = combinedSpec.or(rangeSpec);
    }

    return combinedSpec;
  }

  public Page<Product> fetchProductsWithSpecs(Pageable pageable, ProductCriteriaDTO productCriteriaDTO) {

    Specification<Product> combinedSpec = Specification.where(null);
    if (NotNullUtil.checkOptionNotNull(productCriteriaDTO.getTarget())) {
      Specification<Product> currentSpecs = ProductSpecs.matchListTarget(productCriteriaDTO.getTarget().get());
      combinedSpec = combinedSpec.and(currentSpecs);
    }

    if (NotNullUtil.checkOptionNotNull(productCriteriaDTO.getFactory())) {
      Specification<Product> currentSpecs = ProductSpecs.matchListFactory(productCriteriaDTO.getFactory().get());
      combinedSpec = combinedSpec.and(currentSpecs);
    }

    if (NotNullUtil.checkOptionNotNull(productCriteriaDTO.getPrice())) {
      Specification<Product> currentSpecs = this.buildPriceSpecification(productCriteriaDTO.getPrice().get());
      combinedSpec = combinedSpec.and(currentSpecs);
    }

    return this.productRepository.findAll(combinedSpec, pageable);
  }

  public Page<Product> fetchProducts(Pageable pageable) {
    return this.productRepository.findAll(pageable);
  }

  public Optional<Product> getProductDetailById(Long id) {
    return this.productRepository.findById(id);
  }

  public Cart fetchByUser(User user) {
    Optional<Cart> cartOptional = this.cartRepository.findByUser(user);
    return cartOptional.isPresent() == true ? cartOptional.get() : null;
  }

  public void deleteAProduct(Long id) {
    this.productRepository.deleteById(id);
  }

  public void handleAddProductToCart(String email, Long productId, HttpSession session, Long quantity) {
    User user = this.userService.getUserByEmail(email);
    if (user != null) {
      // check user already have cart ?
      Optional<Cart> cartOptional = this.cartRepository.findByUser(user);

      Cart cart = cartOptional.isPresent() == true ? cartOptional.get() : null;

      if (cart == null) {
        Cart newCart = new Cart();

        newCart.setUser(user);
        newCart.setSum(0);

        cart = this.cartRepository.save(newCart);
      }

      Optional<Product> productOptional = this.productRepository.findById(productId);

      if (productOptional.isPresent()) {
        Product product = productOptional.get();

        // check exist product in cart detail
        CartDetail cartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);

        if (cartDetail == null) {
          CartDetail newCartDetail = new CartDetail();
          newCartDetail.setCart(cart);
          newCartDetail.setProduct(product);
          newCartDetail.setPrice(product.getPrice());
          newCartDetail.setQuantity(quantity);

          this.cartDetailRepository.save(newCartDetail);

          // add sp
          int s = cart.getSum() + 1;
          cart.setSum(s);
          this.cartRepository.save(cart);
          session.setAttribute("sum", s);
        } else {
          // add quantity
          cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
          this.cartDetailRepository.save(cartDetail);
        }
      }

    }
  }

  public void handleDeleteCartDetail(Long cartDetailId, HttpSession session) {

    Optional<CartDetail> cartDetail = this.cartDetailRepository.findById(cartDetailId);
    if (cartDetail.isPresent()) {
      Cart cart = cartDetail.get().getCart();
      this.cartDetailRepository.delete(cartDetail.get());
      // cap nhap sum
      if (cart != null) {
        if (cart.getSum() > 1) {
          cart.setSum(cart.getSum() - 1);
          session.setAttribute("sum", cart.getSum());
          // update db
          this.cartRepository.save(cart);

        } else {
          // delete cart
          session.setAttribute("sum", 0);
          this.cartRepository.delete(cart);
        }
      }
    }
  }

  public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
    for (CartDetail cartDetail : cartDetails) {
      Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
      if (cdOptional.isPresent()) {
        CartDetail currentCartDetail = cdOptional.get();
        currentCartDetail.setQuantity(cartDetail.getQuantity());
        this.cartDetailRepository.save(currentCartDetail);
      }
    }
  }

  public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
      String receiverPhone) {

    // step 1: get cart by user and create order
    Optional<Cart> cartOpt = this.cartRepository.findByUser(user);
    if (cartOpt.isPresent()) {
      List<CartDetail> cartDetails = cartOpt.get().getCartDetails();

      Order order = new Order();
      order.setUser(user);
      order.setReceiverName(receiverName);
      order.setReceiverPhone(receiverPhone);
      order.setReceiverAddress(receiverAddress);
      order.setStatus("PENDING");

      double sum = 0;
      for (CartDetail cd : cartDetails) {
        sum += cd.getPrice();
      }
      order.setTotalPrice(sum);
      order = this.orderRepository.save(order);

      if (cartDetails != null) {
        for (CartDetail cd : cartDetails) {
          OrderDetail orderDetail = new OrderDetail();
          orderDetail.setOrder(order);
          orderDetail.setProduct(cd.getProduct());
          orderDetail.setPrice(cd.getPrice());
          orderDetail.setQuantity(cd.getQuantity());
          this.orderDetailRepository.save(orderDetail);
        }

        // step2: delete cart_detail and cart
        for (CartDetail cd : cartDetails) {
          this.cartDetailRepository.deleteById(cd.getId());
        }

        // step3: delete cart
        this.cartRepository.deleteById(cartOpt.get().getId());

        // reset session
        session.setAttribute("sum", 0);
      }
    }
  }

  public Long countProducts() {
    return this.productRepository.count();
  }
}
