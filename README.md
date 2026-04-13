# Solar Watch Rewrite

A full-stack rewrite of the original Solar Watch project.

## What is included

- Spring Boot 3.3 backend
- JWT authentication with Spring Security
- PostgreSQL database with Spring Data JPA repositories
- Async programming with `@Async` and `CompletableFuture`
- Docker Compose for database, backend, and frontend
- React + Vite frontend
- Search history persisted per authenticated user

## Backend features

### Security function
The backend uses JWT-based authentication:
- `POST /api/auth/register`
- `POST /api/auth/login`
- protected endpoints under `/api/solar/**`
- admin-only endpoint under `/api/admin/**`

### Database repositories
- `UserRepository`
- `SolarQueryRepository`

### Async programming
The solar workflow performs async calls in service layer:
- `GeoCodingService#getCoordinates`
- `SunriseSunsetService#getSolarData`
- composed inside `SolarService`

## Run with Docker Compose

From the project root:

```bash
docker compose up --build
```

App URLs:
- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- Postgres: `localhost:5432`

## Run locally without Docker

### Backend
```bash
cd backend
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Suggested next improvements

- add refresh tokens
- add cached solar lookups to avoid duplicate external requests
- add unit and integration tests for auth and solar history
- add role management admin screens
