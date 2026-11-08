# Entity relationship diagram

```mermaid
erDiagram
  PRODUCT ||--o{ ORDER_ITEM : appears_in
  CUSTOMER_ORDER ||--|{ ORDER_ITEM : contains
  PRODUCT { bigint id PK string name string sku UK decimal price int stockQuantity int reorderLevel string category }
  CUSTOMER_ORDER { bigint id PK string customerName string customerEmail string status datetime createdAt }
  ORDER_ITEM { bigint id PK bigint order_id FK bigint product_id FK int quantity decimal unitPrice }
```

`ORDER_ITEM` is the related join entity: an order has one or more items and a product can appear in many order items. `unitPrice` is captured at order time so later product price changes do not alter historic orders.
