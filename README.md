   # Car Dealer Management System
   [![Car Dealer CI](https://github.com/cigdemkocak/CarDealerManagementSystem/actions/workflows/carDealerCi.yml/badge.svg)](https://github.com/cigdemkocak/CarDealerManagementSystem/actions/workflows/carDealerCi.yml)

A RESTful API built with Spring Boot for managing car dealer operations including user authentication, car sales, and real-time currency conversion.

---

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.5.13**
- **Spring Security 6** — JWT-based authentication
- **Spring Data JPA** — Database operations
- **PostgreSQL** — Relational database
- **Docker & Docker Compose** — Containerization
- **GitHub Actions** — CI pipeline that builds the project and runs all tests on every push
- **JWT (jjwt 0.11.5)** — Token generation and validation
- **Lombok** — Boilerplate reduction
- **Validation** — Request validation
- **Postman** — API testing
- **Testcontainers** — Integration tests against a real PostgreSQL container

---

## ⚙️ Architecture & Features

- **CI/CD Pipeline** — Automated build and test execution (including Testcontainers integration tests) on every push and pull request using GitHub Actions
- **Exception Architecture** — Global exception handling with custom error messages
- **JWT Security** — `JWTService` for token generation/validation, `JWTAuthenticationFilter` for request filtering, `AuthEntryPoint` for unauthorized access handling
- **Authentication** — Register, login and refresh token flows
- **Currency Integration** — Real-time exchange rate fetching from the Central Bank of Turkey (TCMB)
- **Car Sales** — Full car purchase flow with USD currency conversion
- **Testing** — Web-layer tests with JUnit 5 and Mockito, plus end-to-end integration tests with Testcontainers

---
## 🧪 Testing

The project has two kinds of tests.

### Web-layer tests

Controller tests using JUnit 5 and Mockito, isolating controller logic from the service and persistence layers with `@WebMvcTest`.

Coverage includes:

- ✅ Successful customer creation with valid data
- ✅ Input validation failures (400 Bad Request) for missing/empty required fields (firstName, lastName, tckn)
- ✅ Input validation failures for null foreign key references (addressId, accountId)
- ✅ Verification that the service layer is never invoked when validation fails

### Integration tests (Testcontainers)

End-to-end tests start the full Spring Boot application on a random port and run it against a real, disposable **PostgreSQL 15** container. No mocks are used for the web, security, service or persistence layers, so the tests exercise the real HTTP → JWT security → controller → service → repository → database chain. The only mocked component is the external TCMB currency rates service, which keeps the tests independent of the internet and of the daily exchange rate.

**Happy path (ordered flow):**

1. PostgreSQL container is created and running
2. User registers
3. User authenticates and receives a JWT token
4. Address is saved
5. Account is saved
6. Customer is saved
7. Car dealer is saved (with its own address)
8. Car is saved
9. Car is assigned to the dealer
10. Car is bought (car status changes to `SALED`)

**Negative cases:**

- ✅ Saving a customer without a JWT token is rejected
- ✅ Saving a second car dealer with an already used address is rejected
- ✅ Selling an already sold car is rejected

Each response is checked field by field against the request (e.g. names, IBAN, amounts, related entity ids).

**Requirements:** Docker must be running, because Testcontainers starts the PostgreSQL container automatically. The tests do not use the database from `docker-compose.yml` or your local PostgreSQL.

### Technologies

- JUnit 5
- Mockito (`@MockitoBean`)
- Spring Boot Test (`@WebMvcTest`, `@SpringBootTest`)
- Testcontainers (PostgreSQL) with `@ServiceConnection`
- Bean Validation (`@NotBlank`, `@NotNull`)

### Running the tests

```bash
mvn test
```

Tests are also automatically executed on every push via the CI pipeline (GitHub Actions).

## 📌 API Endpoints

### 🔓 Authentication (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | Register a new user |
| POST | `/authenticate` | Login and receive JWT token |
| POST | `/refreshToken` | Refresh an existing JWT token |

### 🔐 Protected Endpoints (Requires JWT)

#### Address
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/rest/api/address/save` | Save a new address |

#### Account
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/rest/api/account/save` | Save a new account |

#### Customer
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/rest/api/customer/save` | Save a new customer |

#### Car Dealer
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/rest/api/carDealer/save` | Save a new car dealer |

#### Car
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/rest/api/car/save` | Save a new car |

#### Dealer Car
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/rest/api/dealerCar/save` | Assign a car to a dealer |

#### Currency Rates
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/rest/api/currency-rates?startDate=DD-MM-YYYY&endDate=DD-MM-YYYY` | Fetch exchange rates from TCMB |

#### Car Sales
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/rest/api/saled-car/save` | Process a car sale |

---

## 🔒 Security Flow

1. User registers via `/register`
2. User logs in via `/authenticate` and receives a **JWT token**
3. JWT token is included in the `Authorization: Bearer <token>` header for protected requests
4. Token can be renewed via `/refreshToken`

---

## 🗄️ Database

- **PostgreSQL** is used as the relational database
- Entities: `User`, `Address`, `Account`, `Customer`, `CarDealer`, `Car`, `DealerCar`, `SaledCar`

---

## 🚀 Getting Started

### Option 1: Running with Docker (Recommended)

Make sure you have [Docker](https://www.docker.com/) and Docker Compose installed on your machine.
1. Clone the repository:
 ```bash
 git clone [https://github.com/cigdemkocak/CarDealerManagementSystem.git](https://github.com/cigdemkocak/CarDealerManagementSystem.git)
 cd CarDealerManagementSystem
``` 
2. Build and run the application using Docker Compose:
```bash
 docker compose up -d --build
```
3. The application will be up and running at http://localhost:8080, and PostgreSQL will run on port 5432.
4. To stop the containers:
```bash
 docker compose down
```
### Option 2: Running Locally (Manual)

### Prerequisites
- Java 17+
- PostgreSQL
- Maven

### Installation

1. Clone the repository:
```bash
git clone https://github.com/cigdemkocak/CarDealerManagementSystem.git
```

2. Configure `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Test with Postman on `http://localhost:8080`
