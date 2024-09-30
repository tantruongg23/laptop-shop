package vn.hoidanit.laptopshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @NotEmpty(message = "Name cannot empty")
  private String name;

  @NotNull(message = "Price cannot empty")
  @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0")
  private Double price;

  @Column(columnDefinition = "MEDIUMTEXT")
  @NotNull
  @NotEmpty(message = "Detail description cannot empty")
  private String detailDesc;

  @NotNull
  @NotEmpty(message = "Short description cannot empty")
  private String shortDesc;

  @NotNull(message = "Quantity cannot empty")
  @Min(value = 1, message = "Quantity must be at least 1")
  private Long quantity;

  private Long sold;
  private String factory;
  private String target;

  private String image;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDetailDesc() {
    return detailDesc;
  }

  public void setDetailDesc(String detailDesc) {
    this.detailDesc = detailDesc;
  }

  public String getShortDesc() {
    return shortDesc;
  }

  public void setShortDesc(String shortDesc) {
    this.shortDesc = shortDesc;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Long getSold() {
    return sold;
  }

  public void setSold(Long sold) {
    this.sold = sold;
  }

  public String getFactory() {
    return factory;
  }

  public void setFactory(String factory) {
    this.factory = factory;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  @Override
  public String toString() {
    return "Product [id=" + id + ", name=" + name + ", price=" + price + ", detailDesc=" + detailDesc + ", shortDesc="
        + shortDesc + ", image=" + image + ", quantity=" + quantity + ", sold=" + sold + ", factory=" + factory
        + ", target=" + target + "]";
  }

}
