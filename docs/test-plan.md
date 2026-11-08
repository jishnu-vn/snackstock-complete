# Test plan and evidence

| Test | Type | Expected result |
|---|---|---|
| Create order with stock available | `OrderServiceTest` unit test | Stock reduces and an item is created |
| Create order exceeding stock | `OrderServiceTest` unit test | Request is rejected and no order is saved |
| Create and search product | `ProductControllerIntegrationTest` | HTTP 201 then searchable JSON result |
| Invalid product values | `validators.test.js` client unit test | Zero price and negative stock rejected |

Run backend tests with `mvn test` from `backend`, and frontend tests with `npm test` from `frontend`. The API integration test starts Spring Boot with H2, so it does not require a running database.
