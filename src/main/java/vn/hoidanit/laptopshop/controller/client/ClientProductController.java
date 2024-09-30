package vn.hoidanit.laptopshop.controller.client;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;

import org.hibernate.validator.cfg.defs.NotNullDef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.utils.NotNullUtil;

@Controller
public class ClientProductController {

  private final ProductService productService;

  public ClientProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/products")
  public String getProductPage(Model model,
      ProductCriteriaDTO productCriteriaDTO,
      HttpServletRequest request) {
    // Handle page param
    int page = 1;
    boolean isPageFalse = false;
    try {
      page = Integer.parseInt(productCriteriaDTO.getPage().get());
    } catch (Exception e) {
      isPageFalse = true;
    }

    // Set page, limit
    Pageable pageable = PageRequest.of(page - 1, 6);
    // check sort
    if (NotNullUtil.checkOptionNotNull(productCriteriaDTO.getSort())) {
      String sort = productCriteriaDTO.getSort().get();
      if (sort.equals("gia-tang-dan")) {
        pageable = PageRequest.of(page - 1, 5, Sort.by(Product_.PRICE).ascending());
      } else if (sort.equals("gia-giam-dan")) {
        pageable = PageRequest.of(page - 1, 6, Sort.by(Product_.PRICE).descending());
      }
    }
    // Get data, set data to model
    Page<Product> pageProducts = this.productService.fetchProductsWithSpecs(pageable, productCriteriaDTO);
    List<Product> products = pageProducts.getContent().size() > 0 ? pageProducts.getContent()
        : new ArrayList<Product>();

    String qs = request.getQueryString();
    if (qs != null && !qs.isBlank()) {
      // remove page
      qs = qs.replace("page=" + page, "");
    }

    model.addAttribute("products", products);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", pageProducts.getTotalPages());
    model.addAttribute("queryString", qs);
    // Return page default if page param is false
    if (isPageFalse == true) {
      return "redirect:/products?page=" + page + "&sort=gia-nothing";
    }

    return "client/product/show";
  }

  @GetMapping("/product/{id}")
  public String getProductDetailPage(@PathVariable Long id, Model model) {
    Product product = this.productService.getProductDetailById(id).get();
    model.addAttribute("product", product);
    model.addAttribute("id", id);
    return "client/product/show-detail";
  }

  @PostMapping("/add-product-to-cart/{id}")
  public String addProductToCart(@PathVariable Long id, HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    Long productId = id;
    String email = (String) session.getAttribute("email");

    this.productService.handleAddProductToCart(email, productId, session, (long) 1);
    return "redirect:/";
  }

  @PostMapping("/add-product-from-view-detail")
  public String addProductToCartFromViewDetail(
      @RequestParam("id") Long id,
      @RequestParam("quantity") Long quantity,
      HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    Long productId = id;
    String email = (String) session.getAttribute("email");

    this.productService.handleAddProductToCart(email, productId, session, quantity);
    return "redirect:/";
  }
}
