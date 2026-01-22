# Microservices Project Overview

## Project Description

This is a Spring Boot microservices architecture project demonstrating a distributed system for managing clients, credit cards, and email notifications. The project uses Spring Cloud technologies including Eureka for service discovery and Spring Cloud Gateway for API routing.

## Architecture

The project follows a microservices architecture pattern with the following components:

```
┌─────────────────┐
│  Cloud Gateway  │ (Port 8080)
│      (MS)       │
└────────┬────────┘
         │
         ├─────────────────┬─────────────────┐
         │                 │                 │
    ┌────▼────┐      ┌────▼────┐      ┌────▼────┐
    │ Client  │      │  Card   │      │  Email  │
    │   MS    │      │   MS    │      │   MS    │
    └────┬────┘      └────┬────┘      └────┬────┘
         │                 │                 │
         └─────────────────┴─────────────────┘
                           │
                    ┌──────▼──────┐
                    │   Eureka    │
                    │   Server    │ (Port 8761)
                    └─────────────┘
```

## Technology Stack

- **Framework**: Spring Boot 3.5.4/3.5.5
- **Java Version**: 17
- **Spring Cloud**: 2025.0.0
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Additional Libraries**:
  - Lombok (for reducing boilerplate code)
  - Spring Data JPA (for database operations)
  - Spring Boot Mail (for email functionality)
  - Spring Boot Actuator (for monitoring in client-ms)

## Microservices

### 1. Eureka Server (`eureka-server`)
**Port**: 8761  
**Purpose**: Service discovery server that registers and manages all microservices.

**Features**:
- Service registry for all microservices
- Health monitoring of registered services
- Service discovery endpoint

**Configuration**:
- Self-registration disabled (standalone server)
- Registry fetching disabled (doesn't need to discover other Eureka servers)

---

### 2. Cloud Gateway (`cloud-gateway-ms`)
**Port**: 8080  
**Purpose**: API Gateway that routes requests to appropriate microservices.

**Features**:
- Single entry point for all client requests
- Load balancing using Eureka service discovery
- Route configuration:
  - `/clients/**` → routes to `client-ms`
  - `/cards/**` → routes to `card-ms`

**Technology**: Spring Cloud Gateway (reactive, WebFlux-based)

---

### 3. Client Microservice (`client-ms`)
**Port**: Random (0) - dynamically assigned  
**Purpose**: Manages client information.

**Database**: PostgreSQL (`api_test` database)

**Endpoints**:
- `GET /clients` - Get all clients
- `GET /clients?email={email}` - Get client by email
- `POST /clients/add` - Add a new client
- `DELETE /clients/{id}` - Delete a client by ID

**Data Model**:
- `Client`: id, name, email

**Features**:
- Spring Boot Actuator for health monitoring
- H2 database dependency (for testing/development)

---

### 4. Card Microservice (`card-ms`)
**Port**: Random (0) - dynamically assigned  
**Purpose**: Manages credit card information and client-card relationships.

**Database**: PostgreSQL (`api_test` database)

**Endpoints**:
- `GET /cards` - Get all cards
- `GET /cards?creditLimit={limit}` - Get cards with credit limit less than or equal to specified value
- `GET /cards?email={email}` - Get all cards associated with a client email
- `POST /cards/add` - Add a new card

**Data Models**:
- `Card`: id, name, brand (enum), type (enum), creditLimit, availableCredit
- `ClientCard`: id, email, card (relationship), creditLimit
- Enums: `CardBrandEnum`, `CardTypeEnum`

**Features**:
- Many-to-one relationship between ClientCard and Card
- Query cards by client email (cross-service relationship)

---

### 5. Email Microservice (`email-ms`)
**Port**: Random (0) - dynamically assigned  
**Purpose**: Handles email sending functionality.

**Database**: PostgreSQL (`api_test` database)

**Endpoints**:
- `POST /sending-email` - Send an email

**Data Model**:
- `EmailModel`: Stores email information and status
- `StatusEmail`: Enum for email status

**Features**:
- Spring Boot Mail integration
- Email validation using Spring Validation
- SMTP configuration (default: Gmail SMTP)
- Email status tracking

**Configuration**:
- SMTP Host: `smtp.gmail.com` (configurable via `SPRING_MAIL_HOST`)
- SMTP Port: 587 (configurable via `SPRING_MAIL_PORT`)
- Requires `SPRING_MAIL_USERNAME` and `SPRING_MAIL_PASSWORD` environment variables

---

## Database Configuration

All microservices (except Eureka Server and Gateway) use PostgreSQL:
- **Database Name**: `api_test`
- **Default Host**: `localhost:5432`
- **Default Username**: `postgres`
- **Default Password**: `postgres`

**Environment Variables** (can override defaults):
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

**JPA Configuration**:
- `ddl-auto: update` - Automatically updates database schema

---

## Service Discovery

All microservices register with Eureka Server:
- **Eureka Server URL**: `http://localhost:8761/eureka/` (configurable via `EUREKA_CLIENT_DFZONE`)
- Services use dynamic port assignment (port: 0) and register with Eureka
- Gateway uses service discovery to route requests using service names (`lb://client-ms`, `lb://card-ms`)

---

## Project Structure

```
MICROSERVICES/
└── study-project/
    ├── eureka-server/          # Service discovery server
    ├── cloud-gateway-ms/       # API Gateway
    ├── client-ms/              # Client management service
    ├── card-ms/                # Card management service
    └── email-ms/               # Email service
```

Each microservice follows a standard Spring Boot structure:
```
{service-name}/
├── src/
│   ├── main/
│   │   ├── java/com/sskings/
│   │   │   └── {service_package}/
│   │   │       ├── {Service}Application.java
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── service/        # Business logic
│   │   │       ├── repository/     # Data access layer
│   │   │       ├── model/          # Entity models
│   │   │       └── dto/            # Data transfer objects
│   │   └── resources/
│   │       └── application.yaml    # Configuration
│   └── test/                       # Test files
├── pom.xml                         # Maven dependencies
└── target/                         # Build output
```

---

## Running the Project

### Prerequisites
1. Java 17 installed
2. Maven installed
3. PostgreSQL running on localhost:5432
4. Database `api_test` created
5. (For email-ms) SMTP credentials configured

### Startup Order
1. **Eureka Server** (must start first)
   ```bash
   cd study-project/eureka-server
   mvn spring-boot:run
   ```
   Access at: http://localhost:8761

2. **Client Microservice**
   ```bash
   cd study-project/client-ms
   mvn spring-boot:run
   ```

3. **Card Microservice**
   ```bash
   cd study-project/card-ms
   mvn spring-boot:run
   ```

4. **Email Microservice**
   ```bash
   cd study-project/email-ms
   mvn spring-boot:run
   ```

5. **Cloud Gateway** (should start last)
   ```bash
   cd study-project/cloud-gateway-ms
   mvn spring-boot:run
   ```
   Access at: http://localhost:8080

### Environment Variables
Set these if using non-default values:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `EUREKA_CLIENT_DFZONE`
- `SPRING_MAIL_HOST`
- `SPRING_MAIL_PORT`
- `SPRING_MAIL_USERNAME`
- `SPRING_MAIL_PASSWORD`

---

## API Usage Examples

### Through Gateway (Port 8080)

**Get all clients:**
```bash
GET http://localhost:8080/clients
```

**Get client by email:**
```bash
GET http://localhost:8080/clients?email=user@example.com
```

**Add a client:**
```bash
POST http://localhost:8080/clients/add
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com"
}
```

**Get all cards:**
```bash
GET http://localhost:8080/cards
```

**Get cards by credit limit:**
```bash
GET http://localhost:8080/cards?creditLimit=5000
```

**Get cards by client email:**
```bash
GET http://localhost:8080/cards?email=john@example.com
```

**Add a card:**
```bash
POST http://localhost:8080/cards/add
Content-Type: application/json

{
  "name": "Premium Card",
  "brand": "VISA",
  "type": "CREDIT",
  "creditLimit": 10000
}
```

**Send email:**
```bash
POST http://localhost:8080/sending-email
Content-Type: application/json

{
  "ownerRef": "John Doe",
  "emailFrom": "sender@example.com",
  "emailTo": "recipient@example.com",
  "subject": "Test Email",
  "text": "Email body content"
}
```

---

## Key Features

1. **Service Discovery**: All services automatically register with Eureka
2. **Load Balancing**: Gateway uses load balancing for service instances
3. **Dynamic Port Assignment**: Services use random ports and register with Eureka
4. **Database Integration**: JPA/Hibernate with PostgreSQL
5. **Email Functionality**: SMTP email sending with status tracking
6. **RESTful APIs**: Standard REST endpoints for CRUD operations
7. **Logging**: SLF4J logging throughout services
8. **Validation**: Input validation using Spring Validation

---

## Development Notes

- All services use Lombok to reduce boilerplate code
- Services are configured to use environment variables for flexibility
- Database schema is auto-updated on startup (`ddl-auto: update`)
- Services use random ports to allow multiple instances
- Gateway provides a single entry point for all API calls
- Service-to-service communication can be done through Eureka service discovery

---

## Future Enhancements (Potential)

- Add authentication/authorization (Spring Security)
- Implement circuit breakers (Resilience4j)
- Add distributed tracing (Spring Cloud Sleuth/Zipkin)
- Implement API versioning
- Add comprehensive error handling
- Implement service-to-service communication patterns
- Add monitoring and metrics (Prometheus, Grafana)
- Containerization with Docker
- Kubernetes deployment configurations
