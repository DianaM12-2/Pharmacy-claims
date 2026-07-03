# Pharmacy Claims API

A production-style RESTful API for managing pharmacy claims and detecting potential fraud, built with **Java 21**, **Spring Boot 3**, **JPA/Hibernate**, and **OpenAPI/Swagger**.

Inspired by real-world healthcare IT systems that process millions of pharmacy claims per year.

---

## Features

- Full CRUD operations for pharmacy claims
- Automated fraud detection (high-value claims, duplicate patient submissions)
- Real-time analytics endpoint (total amount, average, flagged count)
- Input validation with meaningful error responses
- OpenAPI 3.0 documentation (Swagger UI)
- In-memory H2 database with seed data for easy local testing
- Unit tests with JUnit 5 and Mockito
- Docker support
- GitHub Actions CI/CD pipeline

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.2 |
| ORM | Spring Data JPA / Hibernate |
| Database | H2 (dev), PostgreSQL (prod-ready) |
| Testing | JUnit 5, Mockito |
| Documentation | SpringDoc OpenAPI 3 |
| Containerization | Docker |
| CI/CD | GitHub Actions |

---

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+

### Run locally
```bash
git clone https://github.com/DianaM12-2/pharmacy-claims-api.git
cd pharmacy-claims-api
mvn spring-boot:run
```

The API starts at `http://localhost:8080`

### Run with Docker
```bash
docker build -t pharmacy-claims-api .
docker run -p 8080:8080 pharmacy-claims-api
```

### Run tests
```bash
mvn test
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/claims` | Get all claims |
| GET | `/api/v1/claims/{id}` | Get claim by ID |
| POST | `/api/v1/claims` | Submit new claim |
| PATCH | `/api/v1/claims/{id}/status` | Update claim status |
| DELETE | `/api/v1/claims/{id}` | Delete claim |
| GET | `/api/v1/claims/flagged` | Get flagged claims |
| GET | `/api/v1/claims/patient/{name}` | Get claims by patient |
| GET | `/api/v1/claims/high-value` | Get high-value claims |
| GET | `/api/v1/claims/analytics` | Get analytics summary |

### Interactive docs
Visit `http://localhost:8080/swagger-ui.html` after starting the app.

---

## Fraud Detection Logic

Claims are automatically evaluated on submission:

- **High-value flag**: Amount > $500 → flagged with reason
- **Duplicate detection**: Patient with 3+ existing claims → flagged with reason
- **Status**: Flagged claims receive `FLAGGED` status for review

---

## Example Request

```bash
curl -X POST http://localhost:8080/api/v1/claims \
  -H "Content-Type: application/json" \
  -d '{
    "claimId": "RX-2026-001",
    "patientName": "Maria Garcia",
    "medication": "Lisinopril",
    "amount": 45.00
  }'
```

---

## Author

**Diana Martinez** — [GitHub](https://github.com/DianaM12-2) · [LinkedIn](https://linkedin.com/in/diana-martinez-s)
