# Project: Shopping App Microservices Backend (Java, Spring Boot, Maven, Docker)

## Overview

Built as a set of Java Spring Boot REST APIs, this project provides a comprehensive backend for a shopping application using a microservices architecture. The system is composed of independent services for order management, inventory, product catalog, and an API Gateway for secure, unified access. The application leverages Spring Boot, Spring Data JPA (MySQL), Spring Data MongoDB, Spring Cloud Gateway, and Keycloak for OAuth2-based security. Resilience patterns (Circuit Breaker, Retry, TimeLimiter) are applied via Resilience4j to protect gateway calls and inter-service communication. Each service is containerized with Docker and can be run and scaled independently.

**Key Features:**
- **API Gateway**: Central entry point, routing, and OAuth2 resource server validating JWT tokens.
- **Keycloak**:  Authorization Server (authenticates, issues tokens)
- **Order Service**: Place, view, and manage customer orders. Interacts with Inventory Service to check stock Using Spring Cloud OpenFiegn client.
- **Inventory Service**: Track and update product stock and availability.
- **Product Service**: Manage product catalog and search.
- **Service Isolation**: Each service has its own database and can be developed, deployed, and scaled independently.
- **Containerized Databases**: MySQL for order/inventory, MongoDB for products, all orchestrated via Docker Compose.
- **Resilience & Fault Tolerance**: Circuit Breaker, Retry, and TimeLimiter patterns (via Resilience4j) protect the API Gateway and inter-service calls against transient failures and cascading outages.
## Main Components

- **User Authentication & Authorization**: OAuth2(Client Credentials flow) with Keycloak, API Gateway as resource server.
- **Order Management**: Create and retrieve orders, order history.
- **Inventory Management**: Stock tracking, updates, and availability checks.
- **Product Catalog**: CRUD operations and product search.
- **API Security**: All endpoints secured via Bearer JWT tokens.
- **Resilience & Fault Tolerance**: Resilience4j (circuit breaker, retry, timelimiter) is used across services and the gateway to improve availability and limit cascading failures.
## Tools/Software and Processes

- **Java 21** – OOP, records, streams, lambdas, generics, etc.
- **Spring Boot 3.x** – Microservice development.
- **Spring Data JPA** – Relational persistence (MySQL).
- **Spring Data MongoDB** – NoSQL persistence (MongoDB).
- **Spring Cloud Gateway** – API Gateway and routing. Resource server for OAuth2.
- **Spring Cloud OpenFeign** – Declarative REST client for inter-service communication.
- **Resilience4j** – Fault tolerance library (circuit breaker, retry, timelimiter) used to protect remote calls.
- **Keycloak** – OAuth2 authorization server.
- **Maven** – Build tool and dependency management.
- **Docker & Docker Compose** – Containerization and orchestration.
- **JUnit, Testcontainers, RestAssured, WireMock** – Unit, integration testing and mocking.
- **IntelliJ IDEA**
## Project Structure

```
shopping-app-microservices/
│
├── api-gateway/         # Spring Cloud Gateway, OAuth2 resource server
├── order-service/       # Order management microservice (MySQL)
├── inventory-service/   # Inventory management microservice (MySQL)
├── product-service/     # Product catalog microservice (MongoDB)
```

## Getting Started

1. **Start Databases**  
   Each service has a `docker-compose.yml` for its database.  
   Example for order service:
   ```powershell
   cd order-service
docker-compose up -d
   ```
   Repeat for `inventory-service` and `product-service`.

2. **Start Keycloak for API Gateway**  
   See `api-gateway/docker/keycloak/realms/` for realm configuration.

3. **Build and Run Services**  
   In each service directory:
   ```powershell
   ./mvnw spring-boot:run
   ```

4. **Access APIs**  
   Use Postman or CLI tools to obtain a Bearer token from Keycloak and call the API Gateway endpoints.

## Security

- **OAuth2**: API Gateway enforces authentication and authorization using Keycloak.
- **Client Credentials Flow**: Designed for secure API access from clients like Postman.

## Notes

- Database data directories (e.g., `mysql_data/`) are ignored in version control.
- Each service is fully independent and can be scaled or updated separately.