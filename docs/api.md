# API endpoints

Base URL: `http://localhost:8080/api`. JSON is used for requests and responses.

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/products?q=&lowStock=` | List products; search by name/SKU or filter low stock |
| POST | `/products` | Create a product |
| GET/PUT/DELETE | `/products/{id}` | Read, update, delete product |
| GET | `/orders?status=` | List orders, optionally by status |
| POST | `/orders` | Create a pending room-delivery order |
| GET | `/orders/{id}` | Read an order with its items |
| PATCH | `/orders/{id}/status` | Update status |
| PATCH | `/orders/{id}/payment` | Admin verifies QR payment or records cash collection |
| DELETE | `/orders/{id}` | Delete pending order and restore stock |
| GET | `/reports/low-stock` | Low-stock report |
| GET | `/reports/profit?period=week|month|all` | Live revenue, cost, profit, daily chart data and sales by category from delivered orders |

Create product: `{"name":"Desk Lamp","sku":"LAMP-001","price":24.99,"stockQuantity":12,"reorderLevel":4,"category":"Home"}`

Create room-delivery order: `{"customerName":"Asha Kumar","phoneNumber":"9876543210","roomNumber":"Room 304 Block B","deliveryNote":"Please knock","paymentMethod":"QR_PAYMENT","items":[{"productId":1,"quantity":2}]}`

Status flow: `PENDING → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED`. Use `{"status":"CONFIRMED"}` (or the next status in the flow). Stock is deducted when an approved order is confirmed and restored if it is cancelled before delivery.

Admin payment approval: `{"paymentStatus":"PAYMENT_VERIFIED"}` for QR orders or `{"paymentStatus":"CASH_COLLECTED"}` for cash orders. An order cannot be confirmed until its payment has been manually approved.

Validation failures return HTTP 400 with `{ "message": "clear explanation" }`; missing records return 404.
