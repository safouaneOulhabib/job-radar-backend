# JobRadar Backend

Spring Boot Java 21 backend for the JobRadar SaaS platform.

## Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Validation
- PostgreSQL
- Flyway
- JWT dependencies and configuration placeholders for access and refresh token support

## Architecture

The codebase is structured as a modular monolith under `com.jobradar`.

Modules:

- `identity`
- `candidate`
- `jobs`
- `sources`
- `matching`
- `notifications`
- `shared`

Each module follows a pragmatic clean architecture layout:

- `domain`
- `application`
- `infrastructure`
- `presentation`

## Run PostgreSQL

```bash
docker compose up -d postgres
```

## Run The App

```bash
mvn spring-boot:run
```

The health endpoint is available at:

```text
GET /api/health
```

## Configuration

Defaults are set in `src/main/resources/application.yml` and can be overridden with environment variables:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_ISSUER`
- `JWT_ACCESS_TOKEN_TTL`
- `JWT_REFRESH_TOKEN_TTL`
- `JWT_SECRET`

The local Docker Compose PostgreSQL instance is exposed on host port `55432`.
