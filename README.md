# Solar Watch

A full-stack web application that provides real-time sunrise and sunset information for cities around the world. Users can search by city and date to retrieve accurate solar data through an intuitive and responsive interface.

Built as a modern web application with frontend and backend integration.

---

## Features

- Search sunrise and sunset times by city
- Select custom dates
- Worldwide city support
- Fast API-based data retrieval
- User authentication support (if implemented)
- Responsive design for desktop and mobile
- Weather and solar data integration
- Clean and modern UI

---

## Demo

Add your deployed application URL here:

```bash
https://your-demo-url.com
```

## Tech Stack

### Frontend
- React
- JavaScript

### Backend
- Java Spring Boot
- REST API

### Database
- PostgreSQL

### External APIs
- Sunrise-Sunset API
- Weather API

---

## Getting Started

### Prerequisites

Make sure you have installed:

- Node.js
- npm or yarn
- Java SDK (if using Spring Boot)
- Docker (optional)

---

## Installation

Clone the repository:

```bash
git clone https://github.com/Markkndr/solar-watch.git
cd solar-watch
```

Install dependencies:

```bash
npm install
```

---

## Environment Variables

Create a `.env` file in the root directory.

Example:

```env
PORT=3000
DATABASE_URL=your_database_url
API_KEY=your_api_key
JWT_SECRET=your_secret
```

Update the variables according to your backend setup.

---

## Running the Application

### Development Mode

Frontend:

```bash
npm run dev
```

Backend:

```bash
./mvnw spring-boot:run
```

---

## Building for Production

```bash
npm run build
```

Backend production start:

```bash
npm start
```

---

## Project Structure

```bash
.
├── frontend/           # Frontend application
├── backend/            # Backend server
└── README.md
```
---

## API Example

Example request:

```http
GET /api/sun?city=Budapest&date=2026-05-17
```

Example response:

```json
{
  "sunrise": "05:07",
  "sunset": "20:12"
}
```

---

## Roadmap

Planned improvements:

- [ ] Favorites and saved cities
- [ ] Geolocation support
- [ ] Weather forecast integration
- [ ] Unit and timezone settings

---

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to your branch
5. Open a Pull Request

---

## License

This project is licensed under the MIT License.

---

## Author

Created by [Mark Kondor](https://github.com/Markkndr)
