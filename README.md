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
- PostgreSQL (configurable datasource with Oracle in example)
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
  "status": true
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
  "status": true
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
  "status": true
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
  "status": true
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
  "status": true
}
```

---

#### GET `/api/movie/{id}`

Retrieve movie details by ID.

**Response** (200 OK):
```json
{
  "response": {
    "imdbId": "tt1234567",
    "title": "Movie Title",
    "year": "2020",
    "genre": "Action",
    "director": "Director Name",
    "writer": "Writer Name",
    "actors": "Actor 1, Actor 2",
    "plot": "Movie plot",
    "language": "English",
    "poster": "http://link_to_poster",
    "type": "movie"
  },
  "status": true
}
```

---

#### GET `/api/movie/search`

Search movies in the local database.

**Query Parameters**:
- `title` (optional): Search by title.
- `year` (optional): Filter by release year.
- `director` (optional): Filter by director.

**Response** (200 OK):
```json
{
  "response": [
    {
      "id": 1,
      "imdbID": "tt1234567",
      "title": "Movie Title",
      "year": "2020",
      "type": "movie",
      "poster": "http://link_to_poster"
    }
  ],
  "status": true
}
```

---

### Rating APIs

#### POST `/api/rating/add`

Add a rating to a movie.

**Query Parameters**:
- `movieId` (required): ID of the movie to rate.
- `rating` (required): Rating between 1 and 5.

**Response** (200 OK):
```json
{
  "response": "Rating added successfully for movie ID: 1",
  "status": true
}
```

---

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/movie-management-system.git
   cd movie-management-system
   ```
2. Configure the database connection in `application.yml`.
3. Run the following commands to set up the project:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. Access Swagger UI at `http://localhost:8080/swagger-ui.html`.

---

## Configuration

You can configure the following properties in the `application.yml` file:

- **Database**:
  ```yaml
  spring:
    datasource:
      url: jdbc:oracle:thin:@localhost:1521:xe
      username: movie_app
      password: your_password
      driver-class-name: oracle.jdbc.OracleDriver
  ```
- **OMDB API**:
  ```yaml
  omdb:
    api:
      url: https://www.omdbapi.com
      key: your_api_key
  ```
- **JWT**:
  ```yaml
  movie:
    jwt:
      secret: your_secret_key
      expirationMs: 86400000
  ```

---

## Database Schema and Tables

This project uses Flyway for database migrations. The schema includes:
- **`users`**: Stores user details.
- **`user_roles`**: Stores roles (`ROLE_ADMIN` and `ROLE_USER`).
- **`movies`**: Stores added movies with ratings.
- **`ratings`**: Stores user ratings for movies.

For details about migrations, see the `sql` files under `resources/db/migration`.

---

## License

This project is licensed under the MIT License.