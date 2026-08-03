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
- **GitHub Actions** — CI pipeline for automated build execution
- **JWT (jjwt 0.11.5)** — Token generation and validation
- **Lombok** — Boilerplate reduction
- **Validation** — Request validation
- **Postman** — API testing

---

## ⚙️ Architecture & Features

- **CI/CD Pipeline** — Automated build execution on every push using GitHub Actions
- **Exception Architecture** — Global exception handling with custom error messages
- **JWT Security** — `JWTService` for token generation/validation, `JWTAuthenticationFilter` for request filtering, `AuthEntryPoint` for unauthorized access handling
- **Authentication** — Register, login and refresh token flows
- **Currency Integration** — Real-time exchange rate fetching from the Central Bank of Turkey (TCMB)
- **Car Sales** — Full car purchase flow with USD currency conversion

---

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
