#Spring-MongoDB-embedded: Model One-to-One, One-to-Many Relationships Embedded Documents.

## Architecture
-One user can have multiple products and use has one address.
![architecture](architecture.png)


## Endpoint
-http://localhost:2024/api/orders -Place Order 
-http://localhost:2024/api/orders{firstName} -Get all orders by user first name
-http://localhost:2024/api/orders/addresses/{city} -Get all orders by city 
-http://localhost:2024/api/orders -Update order
-http://localhost:2024/api/orders/{id} -Delete order by user Id

![swagger2](swagger2.png.png)
- http://localhost:2024/swagger-ui/index.html - Swagger2 API documentation 
