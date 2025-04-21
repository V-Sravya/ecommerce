# E-Commerce Backend API

A Spring Boot-based E-Commerce backend API that provides functionality for managing users, products, categories, carts, and orders.

## Features

- User management (create, view, update)
- Category management (create, list, delete)
- Product management (CRUD operations with pagination and search)
- Cart management (add, update, remove items)
- Order management (place order, view orders, update status)
- Mock payment processing
- Global exception handling
- Data validation
- Pagination and sorting support

## Tech Stack

- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- H2 Database (for development)
- Lombok
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on `http://localhost:8080`

## API Endpoints

### User API

- `POST /api/users` - Create a new user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user information

### Category API

- `POST /api/categories` - Create a new category
- `GET /api/categories` - List all categories
- `DELETE /api/categories/{id}` - Delete a category

### Product API

- `POST /api/products` - Create a new product
- `GET /api/products` - List all products (with pagination & sorting)
- `GET /api/products/search?name={name}` - Search products by name
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product

### Cart API

- `POST /api/carts/{userId}/add` - Add product to cart
- `PUT /api/carts/{userId}/update/{productId}` - Update cart item quantity
- `DELETE /api/carts/{userId}/remove/{productId}` - Remove item from cart
- `GET /api/carts/{userId}` - View cart contents

### Order API

- `POST /api/orders/{userId}/place` - Place order from cart
- `GET /api/orders/{userId}` - Get user's orders
- `PUT /api/orders/{orderId}/status` - Update order status

## Request/Response Examples

### Create User
```json
POST /api/users
{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890"
}
```

### Create Product
```json
POST /api/products
{
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "stockQuantity": 10,
    "categoryId": 1
}
```

### Add to Cart
```json
POST /api/carts/1/add
{
    "productId": 1,
    "quantity": 2
}
```

## Error Handling

The API uses a global exception handler to provide consistent error responses:

```json
{
    "success": false,
    "message": "Error message",
    "data": null
}
```

## Pagination

The product listing API supports pagination and sorting:

- `GET /api/products?page=0&size=10&sort=price,asc`
- `GET /api/products/search?name=laptop&page=0&size=10&sort=name,desc`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request 