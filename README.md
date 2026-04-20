# Spring Boot Microservice Starter Kit

A production-ready starter kit for building microservices with **Spring Boot**, **Java**, and **Spring Data JPA**.
It provides a clean layered architecture, automatic API documentation, environment-based configuration, testing setup, and Docker deployment support.

---

## 🚀 Tech Stack

- **Spring Boot 4** — production-ready Java framework with auto-configuration
- **Java 21** — with Lombok to reduce boilerplate
- **Spring Data JPA / Hibernate** — H2 (development/test) and PostgreSQL (production)
- **Swagger / OpenAPI** — auto-generated API documentation via SpringDoc
- **Docker** — multi-stage image and Docker Compose setup
- **JUnit 5 + Mockito + MockMvc** — unit and integration testing
- **Spring Boot Profiles** — environment configuration via `application-{profile}.yml`
- **Spring Boot Actuator** — health check and metrics endpoints
- **Jakarta Bean Validation / Hibernate Validator** — request validation with `@Valid`

---

## ⚡ Getting Started

### Prerequisites

| Tool   | Version  |
| ------ | -------- |
| JDK    | >= 21    |
| Maven  | >= 3.9.x |
| Docker | >= 29.x  |
| Git    | >= 2.x   |

### Setup

```bash
git clone https://github.com/DavideDelBimbo/springboot-microservice-starter-kit.git
cd springboot-microservice-starter-kit

mvn clean install
mvn spring-boot:run
```

The application starts on port `8080` (configurable via `server.port` in `application.yml`).

---

## 🌍 Environment Configuration

Configuration is handled through **Spring Boot Profiles** using environment-specific files located in `src/main/resources/`.

| File                   | Purpose              |
| ---------------------- | -------------------- |
| `application.yml`      | Common / Development |
| `application-prod.yml` | Production           |

The active profile is selected via the `--spring.profiles.active` argument or the `SPRING_PROFILES_ACTIVE` environment variable.

```bash
# Development (default)
mvn spring-boot:run

# Production
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## 📚 API Documentation

Interactive API documentation is generated with **SpringDoc OpenAPI / Swagger UI**.

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

Swagger is enabled in development and automatically disabled in production via the `application-prod.yml` profile:

```yaml
# application-prod.yml
springdoc:
  api-docs:
    enabled: false
  swagger-ui:
    enabled: false
```

---

## 🗄️ Database

The application supports multiple database backends via Spring Data JPA.

- **Development / Test** — H2 in-memory database (no external setup required)
- **Production** — PostgreSQL managed via Docker Compose

The datasource is configured per profile. Production connection parameters are provided through environment variables:

| Variable      | Description       |
| ------------- | ----------------- |
| `DB_HOST`     | PostgreSQL host   |
| `DB_PORT`     | PostgreSQL port   |
| `DB_NAME`     | Database name     |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |

Schema management is handled via `schema.sql` in development and delegated to the PostgreSQL container initialization in production (`ddl-auto: none`).

---

## 🧪 Testing

### Unit Tests

Run isolated tests for mapper, service and controller using Mockito mocks.

```bash
mvn test
```

### Integration Tests

Execute integration tests against a full Spring Boot context backed by an in-memory H2 database, using MockMvc to simulate HTTP requests.

```bash
mvn verify
```

---

## 🐳 Docker

The repository includes a multi-stage Dockerfile and a Docker Compose configuration running the application together with PostgreSQL.

```bash
docker compose up --build
```

Provide the required environment variables before starting:

```bash
export DB_USERNAME=myuser
export DB_PASSWORD=mypassword
docker compose up --build
```

---

## ❤️ Health Check

Health and metrics endpoints are exposed via **Spring Boot Actuator**:

```
GET /actuator/health
GET /actuator/info
GET /actuator/metrics
```

Docker Compose uses `/actuator/health` to verify container readiness before starting the application service.

---

## 📜 License

This project is released under the [Apache License](https://github.com/DavideDelBimbo/springboot-microservice-starter-kit/blob/master/LICENSE).
