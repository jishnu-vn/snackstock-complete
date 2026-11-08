package com.srm.inventory.dto;

import java.math.BigDecimal;
import java.util.List;

/** Data used by the manager's live Insights page. */
public record ProfitReport(
    BigDecimal revenue,
    BigDecimal cost,
    BigDecimal profit,
    BigDecimal profitMargin,
    long deliveredOrders,
    List<RevenuePoint> revenueByDay,
    List<CategorySales> categorySales
) {
  public record RevenuePoint(String label, BigDecimal revenue, BigDecimal profit) {}
  public record CategorySales(String category, BigDecimal revenue, BigDecimal share) {}
}
