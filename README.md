# Movie Management System

The **Movie Management System** is a Spring Boot-based application that allows users to manage movies, search for movies, and manage user authentication. The application integrates with the OMDB API for movie search and offers role-based access control with JWT authentication.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [APIs Documentation](#apis-documentation)
    - [Authentication APIs](#authentication-apis)
    - [OMDB Movie APIs](#omdb-movie-apis)
    - [Movie APIs](#movie-apis)
    - [Rating APIs](#rating-apis)
- [Setup and Installation](#setup-and-installation)
- [Configuration](#configuration)
- [Database Schema and Tables](#database-schema-and-tables)

## Features

1. User Authentication with Role-Based Access Control (Admin/User).
2. Search movies using the **OMDB API**.
3. Manage movies added by admins.
4. Rate movies.
5. JWT-based authentication and authorization.
6. Swagger documentation for exploring APIs.

## Technologies Used

- Java 17
- Spring Boot 3.4.3
- Spring Security with JWT
- Spring Data JPA with Hibernate
- Oracle Database
- Flyway for database migrations
- OpenFeign for API integration
- Lombok for reducing boilerplate code
- Swagger for API documentation

---

## APIs Documentation

### Authentication APIs

#### POST `/api/auth/sign-in`

Authenticate a user and return a JWT.

**Request Body** (JSON):
```json
{
  "email": "user@example.com",
  "password": "your_password"
}
```

**Response** (200 OK):
```json
{
  "response": {
    "token": "jwt_token",
    "id": 1,
    "email": "user@example.com",
    "isAdmin": false
  },
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

#### POST `/api/auth/sign-up`

Register a new user.

**Request Body** (JSON):
```json
{
  "email": "user@example.com",
  "password": "your_password",
  "userName": "username"
}
```

**Response** (200 OK):
```json
{
  "response": {
    "message": "User registered successfully!"
  },
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

### OMDB Movie APIs

#### GET `/omdb/search`

Search movies in the OMDB API. **Admin role required**.

**Query Parameters**:
- `title` (required): Title of the movie to search for.
- `page` (optional): Page number (default = 1).

**Response** (200 OK):
```json
{
  "response": {
    "search": [
      {
        "Title": "Movie Title",
        "Year": "2020",
        "imdbID": "tt1234567",
        "Type": "movie",
        "Poster": "http://link_to_poster"
      }
    ],
    "totalResults": "100",
    "Response": "True"
  },
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

### Movie APIs

#### POST `/api/movie/add`

Add a movie to the database. **Admin role required**.

**Query Parameter**:
- `imdbId` (required): IMDb ID of the movie to add.

**Response** (200 OK):
```json
{
  "response": "Movie with IMDb ID tt1234567 has been added successfully.",
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

#### DELETE `/api/movie/remove`

Remove a movie from the database. **Admin role required**.

**Query Parameter**:
- `movieId` (required): ID of the movie to remove.

**Response** (200 OK):
```json
{
  "response": "Movie with ID 1 has been removed successfully.",
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

#### GET `/api/movie`

Retrieve all movies with pagination.

**Query Parameters**:
- `page` (optional, default = 1): Page number.
- `size` (optional, default = 10): Number of movies per page.

**Response** (200 OK):
```json
{
  "response": {
    "totalMovies": 100,
    "totalPages": 10,
    "currentPage": 1,
    "content": [
      {
        "id": 1,
        "imdbID": "tt1234567",
        "title": "Movie Title",
        "year": "2020",
        "type": "movie",
        "poster": "http://link_to_poster"
      }
    ]
  },
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

#### GET `/api/movie/{id}`

Retrieve movie details by ID.

**Response** (200 OK):
```json
{
  "response": {
    "imdbID": "tt10005330",
    "Title": "The 'Magic-Cat' and the 'Tough-Spider'",
    "Year": "2020",
    "Type": "movie",
    "Poster": "http://link_to_poster"
  },
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

### Rating APIs

#### POST `/api/rating/add`

Add a rating for a movie by a user.

**Request Body** (JSON):
```json
{
  "movieId": 1,
  "rating": 5
}
```

**Response** (200 OK):
```json
{
  "response": "Rating for movie with ID 1 has been added successfully.",
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

#### GET `/api/rating/movie/{movieId}`

Retrieve all ratings for a specific movie.

**Response** (200 OK):
```json
{
  "response": [
    {
      "userId": 1,
      "rating": 4
    },
    {
      "userId": 2,
      "rating": 5
    }
  ],
  "status": true,
  "currentDate": "2023-10-10T10:00:00.000Z"
}
```

---

## Setup and Installation

1. Clone the repository.
2. Configure the database and API keys in the application properties.
3. Run migrations using Flyway.
4. Start the Spring Boot application.

## Configuration

Update `application.properties` with the following parameters:
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `jwt.secret`
- `omdb.api.key`

## Database Schema and Tables

The system uses a PostgreSQL database with the following main tables:
- `users`
- `movies`
- `ratings`
- `roles`