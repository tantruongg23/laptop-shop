package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.RoleService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {

  private final ProductService productService;
  private final UploadService uploadService;

  public ProductController(
      ProductService productService,
      RoleService roleService, UploadService uploadService) {
    this.productService = productService;
    this.uploadService = uploadService;

  }

  @GetMapping("/admin/product")
  public String getListproducts(Model model,
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
    Page<Product> pageUsers = this.productService.fetchProducts(pageable);
    List<Product> products = pageUsers.getContent();
    model.addAttribute("products", products);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", pageUsers.getTotalPages());

    // Return page default if page param is false
    if (isPageFalse == true) {
      return "redirect:/admin/product?page=" + page;
    }

    return "admin/product/show";
  }

  @GetMapping("/admin/product/{id}")
  public String getProductDetail(@PathVariable Long id, Model model) {
    Product product = productService.getProductDetailById(id).get();
    model.addAttribute("product", product);

    return "admin/product/show-detail";
  }

  @GetMapping("/admin/product/create")
  public String getCreateProductPage(Model model) {
    model.addAttribute("newProduct", new Product());

    return "admin/product/create";
  }

  @PostMapping("/admin/product/create")
  public String createProductPage(Model model,
      @ModelAttribute("newProduct") @Valid Product pr,
      BindingResult newProductBindingResult,
      @RequestParam("tantrFile") MultipartFile file) {

    if (newProductBindingResult.hasErrors()) {
      List<FieldError> errors = newProductBindingResult.getFieldErrors();
      for (FieldError error : errors) {
        System.out.println(error.getField() + " - " + error.getDefaultMessage());
      }
      return "/admin/product/create";
    }

    String image = this.uploadService.handleUploadFile(file, "product");
    pr.setImage(image);

    this.productService.handleSaveProduct(pr);

    return "redirect:/admin/product";
  }

  @GetMapping("/admin/product/update/{id}")
  public String updateProductPage(@PathVariable Long id, Model model) {
    Optional<Product> currentProduct = productService.getProductDetailById(id);
    model.addAttribute("newProduct", currentProduct.get());

    return "admin/product/update";
  }

  @PostMapping("/admin/product/update")
  public String updateProduct(@ModelAttribute("newProduct") Product pr,
      BindingResult newProductBindingResult,
      @RequestParam("tantrFile") MultipartFile file) {

    // validate
    if (newProductBindingResult.hasErrors()) {
      return "/admin/product/update";
    }

    try {
      Product currentProduct = this.productService.getProductDetailById(pr.getId()).get();

      if (currentProduct != null) {

        if (!file.isEmpty()) {
          String image = this.uploadService.handleUploadFile(file, "product");
          currentProduct.setImage(image);
        }
        currentProduct.setName(pr.getName());
        currentProduct.setPrice(pr.getPrice());
        currentProduct.setDetailDesc(pr.getDetailDesc());
        currentProduct.setShortDesc(pr.getShortDesc());
        currentProduct.setQuantity(pr.getQuantity());
        currentProduct.setFactory(pr.getFactory());
        currentProduct.setTarget(pr.getTarget());

        this.productService.handleSaveProduct(currentProduct);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "redirect:/admin/product";
  }

  @GetMapping("/admin/product/delete/{id}")
  public String deleteUserPage(@PathVariable Long id, Model model) {
    Product product = new Product();
    product.setId(id);
    model.addAttribute("newProduct", product);
    return "admin/product/delete";
  }

  @PostMapping("/admin/product/delete")
  public String deleteUser(@ModelAttribute("newProduct") Product pr) {
    this.productService.deleteAProduct(pr.getId());
    return "redirect:/admin/product";
  }
}
