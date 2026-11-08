package com.srm.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime; import java.util.*;

@Entity @Table(name = "customer_orders")
public class CustomerOrder {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(unique=true, nullable=false, updatable=false) private String orderReference;
  @NotBlank @Size(max = 100) private String customerName;
  @Email @Size(max = 120) private String customerEmail;
  @NotBlank @Pattern(regexp="[0-9]{10}", message="must contain exactly 10 digits") private String phoneNumber;
  @NotBlank @Size(max = 80) private String roomNumber;
  @Size(max = 50) private String hostelBlock;
  @Size(max = 250) private String deliveryNote;
  @Enumerated(EnumType.STRING) private PaymentMethod paymentMethod = PaymentMethod.CASH_ON_DELIVERY;
  @Enumerated(EnumType.STRING) private PaymentStatus paymentStatus = PaymentStatus.CASH_PENDING;
  @Size(max=80) private String transactionReference;
  private Integer estimatedDeliveryMinutes = 20;
  @Enumerated(EnumType.STRING) private OrderStatus status = OrderStatus.PENDING;
  private LocalDateTime createdAt = LocalDateTime.now();
  @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true) private List<OrderItem> items = new ArrayList<>();
  @PrePersist void prepareReference(){if(orderReference==null)orderReference="SNK-ORD-"+UUID.randomUUID().toString().substring(0,8).toUpperCase();}
  public Long getId(){return id;} public void setId(Long v){id=v;} public String getOrderReference(){return orderReference;} public void setOrderReference(String v){orderReference=v;} public String getCustomerName(){return customerName;} public void setCustomerName(String v){customerName=v;} public String getCustomerEmail(){return customerEmail;} public void setCustomerEmail(String v){customerEmail=v;} public String getPhoneNumber(){return phoneNumber;} public void setPhoneNumber(String v){phoneNumber=v;} public String getRoomNumber(){return roomNumber;} public void setRoomNumber(String v){roomNumber=v;} public String getHostelBlock(){return hostelBlock;} public void setHostelBlock(String v){hostelBlock=v;} public String getDeliveryNote(){return deliveryNote;} public void setDeliveryNote(String v){deliveryNote=v;} public PaymentMethod getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(PaymentMethod v){paymentMethod=v;} public PaymentStatus getPaymentStatus(){return paymentStatus;} public void setPaymentStatus(PaymentStatus v){paymentStatus=v;} public String getTransactionReference(){return transactionReference;} public void setTransactionReference(String v){transactionReference=v;} public Integer getEstimatedDeliveryMinutes(){return estimatedDeliveryMinutes;} public void setEstimatedDeliveryMinutes(Integer v){estimatedDeliveryMinutes=v;} public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus v){status=v;} public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;} public List<OrderItem> getItems(){return items;} public void setItems(List<OrderItem> v){items=v;}
}
