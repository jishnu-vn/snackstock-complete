package com.srm.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity @Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @NotBlank @Size(max = 100) private String name;
  @NotBlank @Size(max = 30) private String sku;
  @NotNull @DecimalMin(value = "0.0", inclusive = false) private BigDecimal price;
  @DecimalMin(value = "0.0", inclusive = false) private BigDecimal costPrice;
  @NotNull @Min(0) private Integer stockQuantity;
  @NotNull @Min(0) private Integer reorderLevel;
  @Size(max = 50) private String category;
  @Size(max = 500) private String imageUrl;
  public Long getId(){return id;} public void setId(Long v){id=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getSku(){return sku;} public void setSku(String v){sku=v;} public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;} public BigDecimal getCostPrice(){return costPrice;} public void setCostPrice(BigDecimal v){costPrice=v;} public Integer getStockQuantity(){return stockQuantity;} public void setStockQuantity(Integer v){stockQuantity=v;} public Integer getReorderLevel(){return reorderLevel;} public void setReorderLevel(Integer v){reorderLevel=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
}
