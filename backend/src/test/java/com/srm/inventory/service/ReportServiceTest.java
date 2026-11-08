package com.srm.inventory.service;

import com.srm.inventory.entity.*;
import com.srm.inventory.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {
  @Test void calculatesRevenueCostProfitAndCategoryFromDeliveredOrders() {
    OrderRepository repository = mock(OrderRepository.class);
    Product product = new Product(); product.setCategory("Chips"); product.setCostPrice(new BigDecimal("20"));
    OrderItem item = new OrderItem(); item.setProduct(product); item.setQuantity(2); item.setUnitPrice(new BigDecimal("35")); item.setUnitCost(new BigDecimal("20"));
    CustomerOrder order = new CustomerOrder(); order.setStatus(OrderStatus.DELIVERED); item.setOrder(order); order.getItems().add(item);
    when(repository.findByStatus(OrderStatus.DELIVERED)).thenReturn(List.of(order));

    var report = new ReportService(repository).profit("all");

    assertEquals(new BigDecimal("70.00"), report.revenue());
    assertEquals(new BigDecimal("40.00"), report.cost());
    assertEquals(new BigDecimal("30.00"), report.profit());
    assertEquals(1, report.deliveredOrders());
    assertEquals("Chips", report.categorySales().get(0).category());
    assertEquals(new BigDecimal("100.0"), report.categorySales().get(0).share());
  }
}
