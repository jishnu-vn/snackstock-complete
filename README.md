# StockWise Inventory and Order Tracker

SnackStock is a beginner-friendly full-stack project for a hostel snacks business. It maintains product stock, accepts room-delivery orders, supports demo QR or cash payments with manual admin approval, updates order status, and provides low-stock and profit views.

## Requirements covered

- React views: customer menu/order, admin dashboard, products, hostel orders, quick sale, and profit analysis
- Related CRUD: `Product`, `CustomerOrder`, and `OrderItem`
- Client validation and Spring validation with clear JSON errors
- Service rules: unique SKU, sufficient stock, 10-digit phone and room validation, payment approval before confirmation, restricted status changes, stock restoration on pending-order deletion
- Controller → Service → Repository → relational database layers
- Unit, frontend validation, and controller/integration tests
- Separate REST API service boundary documented in `docs/api.md`

## Local run

Prerequisites: Java 17+, Maven 3.9+, Node 20+, npm. PostgreSQL is optional for the default local H2 demonstration database. Local data is stored in `backend/data/snackstock` and survives a normal restart.

```bash
# terminal 1
cd backend
mvn spring-boot:run

# terminal 2
cd frontend
copy .env.example .env
npm install
npm run dev
```

Open the Vite URL (normally `http://localhost:5173`). Demo products are seeded automatically. H2 console is available at `http://localhost:8080/h2-console` while running locally.

## Tests

```bash
cd backend && mvn test
cd frontend && npm install && npm test
```

## Database

For PostgreSQL, create a database and apply `database/schema.sql` followed by `database/seed.sql`. Set:

```bash
DATABASE_URL=jdbc:postgresql://host:5432/stockwise
DATABASE_USERNAME=your_user
DATABASE_PASSWORD=your_password
CORS_ORIGIN=https://your-vercel-site.vercel.app
PORT=8080
```

Use a managed PostgreSQL host such as Neon, Supabase, Railway, or Render. Do not commit `.env` files or passwords.

## Deployment

The frontend and API are separate deployments: a conventional long-running Spring Boot server is not deployed as a normal Vercel frontend.

1. Deploy `backend` to Render, Railway, Fly.io, or another Java host. Build: `mvn clean package -DskipTests`. Start: `java -jar target/inventory-api-1.0.0.jar`. Configure PostgreSQL variables.
2. Deploy `frontend` to Vercel. Set root directory `frontend`, preset Vite, and `VITE_API_BASE_URL` to `https://your-api-host/api`.
3. Set the API's `CORS_ORIGIN` to the exact Vercel URL and redeploy it.
4. Test product creation, a new order, stock decrease, and the low-stock report in the live UI.

## Presentation material

See `docs/architecture.md`, `docs/erd.md`, `docs/wireframes.md`, `docs/api.md`, and `docs/test-plan.md` for diagrams, API evidence, wireframes, and tests.
