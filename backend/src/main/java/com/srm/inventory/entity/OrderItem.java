package com.srm.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity @Table(name="order_items")
public class OrderItem {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @JsonIgnore @ManyToOne(optional=false) @JoinColumn(name="order_id") private CustomerOrder order;
  @ManyToOne(optional=false) @JoinColumn(name="product_id") private Product product;
  @NotNull @Min(1) private Integer quantity;
  @NotNull private BigDecimal unitPrice;
  private BigDecimal unitCost;
  public Long getId(){return id;} public void setId(Long v){id=v;} public CustomerOrder getOrder(){return order;} public void setOrder(CustomerOrder v){order=v;} public Product getProduct(){return product;} public void setProduct(Product v){product=v;} public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;} public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal v){unitPrice=v;} public BigDecimal getUnitCost(){return unitCost;} public void setUnitCost(BigDecimal v){unitCost=v;}
}
