<div align="center">

# 🌅 SolarWatch 🌇

### Know exactly when the sun rises and sets — anywhere, any day

A full-stack web app that returns **sunrise & sunset times** for any city on any date, in local or UTC time, with user accounts and a personal search history.

<br>

<img src="https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21">
<img src="https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.3.5">
<img src="https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black" alt="React 18">
<img src="https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">
<img src="https://img.shields.io/badge/Docker_Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Compose">

</div>

---

## 📑 Table of Contents

- [About](#-about)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [How It Works](#-how-it-works)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [API Reference](#-api-reference)
- [Project Structure](#-project-structure)

---

## ☀️ About

**SolarWatch** is a Spring Boot Web API (with a React frontend) that tells you the **sunrise and sunset times** for a given city on a given date. Enter a city, pick a date, and SolarWatch geocodes the location, fetches solar data, and returns the times in your chosen timezone format.

Every authenticated query is saved, so you build up a personal history of the places and dates you've looked up. Admins get a small stats endpoint to keep an eye on usage.

---

## ✨ Features

- 🌍 **City + date lookup** — sunrise & sunset for any location on any day
- 🕐 **Local or UTC times** — view results in the format you need
- 🔐 **JWT authentication** — register, log in, and access protected endpoints securely
- 👮 **Role-based access** — `USER` and `ADMIN` roles, with an admin-only stats endpoint
- 📜 **Personal history** — every lookup you make is stored and retrievable
- 🩺 **Production-ready** — Spring Boot Actuator for health/metrics, validation, and centralized error handling
- 🐳 **One-command setup** — full stack runs via Docker Compose

---

## 🛠 Tech Stack

| Layer | Technologies |
|-------|--------------|
| **Frontend** | React 18, Vite, React Router 6 |
| **Backend** | Java 21, Spring Boot 3.3.5, Spring WebFlux (`WebClient`), Spring Security, Spring Data JPA, Validation, Actuator |
| **Auth** | JWT (jjwt) |
| **Database** | PostgreSQL 16 |
| **External APIs** | [Open-Meteo Geocoding](https://open-meteo.com/) · [Sunrise-Sunset.org](https://sunrise-sunset.org/api) |
| **Tooling** | Maven, Docker, Docker Compose |

---

## 🔎 How It Works

```
  You ──► "Sunrise in Budapest on 2025-06-21?"
                       │
                       ▼
            ┌─────────────────────┐
            │   SolarWatch API    │
            │   (Spring Boot)     │
            └──────────┬──────────┘
                       │ 1. Geocode city  ──►  Open-Meteo Geocoding API
                       │ 2. Fetch solar data ─►  Sunrise-Sunset.org API
                       │ 3. Save query to history (PostgreSQL)
                       ▼
        🌅 sunrise + 🌇 sunset times (local / UTC)
```

---

## 🚀 Getting Started

### Option A — Docker Compose (recommended)

The fastest way to run the whole stack (database + backend + frontend):

```bash
git clone https://github.com/Markkndr/solar-watch.git
cd solar-watch
docker compose up --build
```

| Service | URL |
|---------|-----|
| Frontend | http://localhost:5173 |
| Backend API | http://localhost:8080 |
| PostgreSQL | localhost:5432 |

> ⚠️ The default JWT secret in `docker-compose.yml` is for local development only — replace it before deploying anywhere public.

### Option B — Run locally

**Prerequisites:** Java 21+, Maven, Node.js 18+, and a running PostgreSQL instance.

**Backend**

```bash
cd backend
# set the environment variables below, then:
./mvnw spring-boot:run
```

**Frontend**

```bash
cd frontend
npm install
npm run dev
```

---

## ⚙️ Configuration

The backend is configured via environment variables (see `backend/src/main/resources/application.yml`):

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC URL | — |
| `SPRING_DATASOURCE_USERNAME` | DB username | — |
| `SPRING_DATASOURCE_PASSWORD` | DB password | — |
| `APP_JWT_SECRET` | Base64 JWT signing secret | — |
| `APP_JWT_EXPIRATION_MS` | Token lifetime in ms | `86400000` (24h) |
| `APP_CORS_ALLOWED_ORIGIN` | Allowed frontend origin | `http://localhost:5173` |
| `APP_GEOCODING_URL` | Geocoding API endpoint | Open-Meteo default |
| `APP_SUNRISE_SUNSET_URL` | Solar data API endpoint | Sunrise-Sunset default |

---

## 📡 API Reference

### Authentication — `/api/auth`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/register` | Create a new account |
| `POST` | `/login` | Log in and receive a JWT |

### Solar data — `/api/solar`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/?city={city}&date={date}` | Get sunrise/sunset for a city on a date |
| `GET` | `/history` | Your saved query history *(authenticated)* |

### Admin — `/api/admin`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/stats` | Usage statistics *(admin only)* |

---

## 📂 Project Structure

```
solar-watch/
├── backend/                      # Spring Boot Web API
│   ├── src/main/java/.../solarwatch/
│   │   ├── client/               # External API response models
│   │   ├── config/               # Security, CORS, WebClient config
│   │   ├── controller/           # Auth, Solar, Admin endpoints
│   │   ├── dto/                  # Request/response DTOs
│   │   ├── exception/            # Custom exceptions + global handler
│   │   ├── model/                # AppUser, Role, SolarQuery
│   │   ├── repository/           # JPA repositories
│   │   ├── security/             # JWT filter, service, user details
│   │   └── service/              # Solar, GeoCoding, SunriseSunset, Auth
│   └── Dockerfile
├── frontend/                     # React + Vite client
│   └── src/
│       ├── pages/                # Dashboard, Login, Register
│       ├── context/              # Auth context
│       └── api/                  # API client
└── docker-compose.yml
```

---

<div align="center">

Made for sky-watchers 🌤️ — never miss a sunrise again.

</div>
