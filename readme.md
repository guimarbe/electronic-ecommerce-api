# Electronic Ecommerce Application

This project is a backend application for an **e-commerce platform** designed to handle product management, discount calculations, and user requests efficiently. It follows a clean and modular architecture inspired by **Domain-Driven Design (DDD)** principles to ensure scalability, maintainability, and testability.

---

## Features

- **Product Management**: Retrieve paginated product data filtered by categories.
- **Discount Calculation**: Automatically applies the best available discount based on predefined business rules.
- **Scalable Architecture**: Modular and layer-based structure, based on hexagonal architecture making, it easy to extend or adapt to future requirements.

---

## Technology Stack

- **Java 21**
- **Spring Boot 3**
- **Hibernate / JPA** for database interactions.
- **Flyway** for database migration.
- **H2 Database** (in-memory, for development and testing).
- **JUnit 5** and **Mockito** for unit testing.
- **Mockmvc** for integration testing.
- **Maven** for dependency management.

---

## Basic Application Startup Guide

### Prerequisites

1. **Java 21**: Ensure you have JDK 17 installed.
2. **Maven**: For building and running the application.
3. A terminal or IDE (such as IntelliJ IDEA or Eclipse).

### Steps to Run the Application

1. **Clone the repository**:
    ```bash
    git clone https://github.com/guimarbe/electronic-ecommerce.git
    cd electronic-ecommerce
    ```

2. **Build the application**:
    ```bash
    mvn clean install
    ```
3. **Flyway Migration**
   - Flyway will automatically create the database tables and seed the initial data when the application starts. The src/main/resources/db/migration folder contains the Category and Product tables,
   and 30 initial entries for categories and products.

4. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

5. **Access the application**:
   - **API Documentation**: Open your browser and navigate to:
   ```bash
   http://localhost:8080/swagger-ui/index.html
   ```

6. **API Endpoints**:
   - Get all products: `GET /api/products?category=electronics&page=0&size=10&sort=name,asc`
   - More endpoints and request examples can be found in the [API Documentation](#).

### Running Tests

To run unit and integration tests:
```bash
mvn test
```

---

# Architectural Decisions

This application was designed following clean architecture principles and Domain-Driven Design (DDD) to provide a robust and maintainable structure,
with a strong focus on encapsulating business rules in the domain layer while keeping external concerns separate. Key Architectural Highlights:

## Layered Design, Scalability and Separation of Concerns
Business rules are decoupled from infrastructure and presentation layers, making the application flexible to changes (e.g., switching databases or exposing new APIs).
We divided this layers into:
   - **Application**: Handles use cases and orchestrates requests between domain and infrastructure. We allocate here the ProductController,
  ResponseDto's, CustomException class, Mapper's, and some pagination utils.
   - **Domain**: Encapsulates core business logic and domain models. Services like **ProductService** and **DiscountService** reside here to enforce business rules.
   - **Infrastructure**: Manages database operations and external system interactions. Main classes like repositories and config classes are allocated here.

In addition, modular design enables the addition of new features (e.g., payment processing, voucher service, user accounts) with minimal impact on existing code.

_Note_: while we’ve adhered to clean architecture, we deliberately omitted Ports and Adapters for simplicity.
This decision avoids unnecessary abstractions, given the application’s scope and direct interaction with Spring-based infrastructure.
This simplification also reduces development overhead while maintaining clean separations between layers.

## Service Abstractions
Interfaces are used for services to promote dependency inversion and facilitate testing.
Separating these services ensures flexibility in applying discounts without tightly coupling them to product management.
   - **ProductService** handles product-related logic, and focuses on fetching and managing products.
   - **DiscountService** encapsulates discount rules independently, promoting single responsibility and reusability.

## Specification Pattern for Pagination
The Specification pattern is used for pagination and filtering to decouple query logic from repositories.
This makes the codebase more modular and allows the addition of complex filters without altering repository methods.

## JPA repository
In this application, the JpaRepository leverages the built-in capabilities of JPA while keeping the business-specific query logic separate from low-level CRUD operations,
and integrate the Specification pattern for more dynamic queries.
The separation of repository interfaces and implementations ensures modularity, testability, and maintainability.

## Flyway for Database Migrations
Flyway ensures that database schema migrations are version-controlled and consistent across environments. 
The initial schema and data are located in src/main/resources/db/migration and we use it create the tables and load some initial data for testing purposes.

## OpenAPI for API Documentation
OpenAPI is used (via Swagger UI) to document the API because it provides a standardized, machine-readable format for API documentation.
This improves developer experience by enabling interactive exploration and testing of endpoints.