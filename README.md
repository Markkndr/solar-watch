Features
 -Search sunrise and sunset times by city
 -Select custom dates
 -Worldwide city support
 -Fast API-based data retrieval
 -User authentication support (if implemented)
 -Responsive design for desktop and mobile
 -Weather and solar data integration
 -Clean and modern UI

Frontend

React / Angular / Vue
TypeScript / JavaScript
Tailwind CSS / CSS Modules

Backend

Java Spring Boot / Node.js / Express
REST API

Database

PostgreSQL / MongoDB / MySQL

External APIs

Sunrise-Sunset API
Weather API

Getting Started

Prerequisites

Make sure you have installed:

Node.js
npm or yarn
Java SDK (if using Spring Boot)
Docker (optional)


Installation

Clone the repository:

git clone https://github.com/Markkndr/solar-watch.git
cd solar-watch

Install dependencies:

npm install

or

yarn install

Environment Variables

Create a .env file in the root directory.

Example:

PORT=3000
DATABASE_URL=your_database_url
API_KEY=your_api_key
JWT_SECRET=your_secret

Update the variables according to your backend setup.

Running the Application

Development Mode

Frontend:

npm run dev

Backend:

npm run start

or for Spring Boot:

./mvnw spring-boot:run
Building for Production
npm run build

Backend production start:

npm start
Project Structure
.
├── frontend/           # Frontend application
├── backend/            # Backend server
└── README.md

API Example

Example request:

GET /api/sun?city=Budapest&date=2026-05-17

Example response:

{
  "sunrise": "05:07",
  "sunset": "20:12"
}
Roadmap

Planned improvements:

 Favorites and saved cities
 Geolocation support
 Weather forecast integration
 Unit and timezone settings

Example Docker build:

docker build -t solar-watch .
Contributing

License

This project is licensed under the MIT License.

Author

Created by Mark Kondor
