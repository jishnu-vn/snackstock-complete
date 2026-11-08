package com.srm.inventory.service;

import com.srm.inventory.dto.ProfitReport;
import com.srm.inventory.entity.*;
import com.srm.inventory.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportService {
  private final OrderRepository orders;
  public ReportService(OrderRepository orders) { this.orders = orders; }

  public ProfitReport profit(String period) {
    LocalDate today = LocalDate.now();
    LocalDate start = switch (period == null ? "month" : period.toLowerCase()) {
      case "week" -> today.minusDays(6);
      case "all" -> LocalDate.of(2000, 1, 1);
      default -> today.withDayOfMonth(1);
    };
    List<CustomerOrder> delivered = orders.findByStatus(OrderStatus.DELIVERED).stream()
        .filter(order -> !order.getCreatedAt().toLocalDate().isBefore(start))
        .toList();

    BigDecimal revenue = BigDecimal.ZERO, cost = BigDecimal.ZERO;
    Map<LocalDate, BigDecimal[]> byDay = new TreeMap<>();
    Map<String, BigDecimal> byCategory = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    for (CustomerOrder order : delivered) {
      LocalDate date = order.getCreatedAt().toLocalDate();
      BigDecimal[] day = byDay.computeIfAbsent(date, ignored -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
      for (OrderItem item : order.getItems()) {
        BigDecimal itemRevenue = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        // Older orders did not store buying cost. Fall back to the product cost, then a visible 72% estimate.
        BigDecimal unitCost = item.getUnitCost() != null ? item.getUnitCost()
            : item.getProduct().getCostPrice() != null ? item.getProduct().getCostPrice()
            : item.getUnitPrice().multiply(new BigDecimal("0.72"));
        BigDecimal itemCost = unitCost.multiply(BigDecimal.valueOf(item.getQuantity()));
        revenue = revenue.add(itemRevenue); cost = cost.add(itemCost);
        day[0] = day[0].add(itemRevenue); day[1] = day[1].add(itemRevenue.subtract(itemCost));
        String category = item.getProduct().getCategory();
        byCategory.merge(category == null || category.isBlank() ? "Other" : category, itemRevenue, BigDecimal::add);
      }
    }
    BigDecimal profit = revenue.subtract(cost);
    BigDecimal margin = revenue.signum() == 0 ? BigDecimal.ZERO
        : profit.multiply(BigDecimal.valueOf(100)).divide(revenue, 1, RoundingMode.HALF_UP);
    DateTimeFormatter labels = DateTimeFormatter.ofPattern("dd MMM");
    List<ProfitReport.RevenuePoint> points = byDay.entrySet().stream()
        .map(entry -> new ProfitReport.RevenuePoint(entry.getKey().format(labels), money(entry.getValue()[0]), money(entry.getValue()[1])))
        .toList();
    BigDecimal totalRevenue = revenue;
    List<ProfitReport.CategorySales> categories = byCategory.entrySet().stream()
        .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
        .map(entry -> new ProfitReport.CategorySales(entry.getKey(), money(entry.getValue()),
            totalRevenue.signum() == 0 ? BigDecimal.ZERO : entry.getValue().multiply(BigDecimal.valueOf(100)).divide(totalRevenue, 1, RoundingMode.HALF_UP)))
        .toList();
    return new ProfitReport(money(revenue), money(cost), money(profit), margin, delivered.size(), points, categories);
  }

  private BigDecimal money(BigDecimal value) { return value.setScale(2, RoundingMode.HALF_UP); }
}
