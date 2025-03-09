# Movie Management System

The **Movie Management System** is a Spring Boot-based application that allows users to manage movies, search for movies, and manage user authentication. The application integrates with the OMDB API for movie search and offers role-based access control with JWT authentication.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Database Schema and Tables](#database-schema-and-tables)
- [APIs Documentation](#apis-documentation)
  - [Authentication APIs](#authentication-apis)
  - [OMDB Movie APIs](#omdb-movie-apis)
  - [Movie APIs](#movie-apis)
  - [Rating APIs](#rating-apis)

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
## Setup and Installation

### Prerequisites

1. **Java 17** must be installed.
2. **Docker** must be installed and running.
3. Install a database client (e.g., **DBeaver**, **IntelliJ Database Tool**, etc.) if you want to manage the Oracle database manually.

---

### Setting Up the Oracle Database

The application requires an Oracle database to function. Follow these steps to set up an Oracle database using the `gvenzl/oracle-xe` Docker image:

#### Step 1: Pull the Oracle XE Docker Image

Run the following command to pull the lightweight Oracle Express Edition (Oracle XE) image:

```bash
docker pull gvenzl/oracle-xe
```

#### Step 2: Start the Oracle XE Docker Container

Run the following command to start the container:

```bash
docker run -d -p 1521:1521 --name oracle-xe -e ORACLE_PASSWORD=1234 gvenzl/oracle-xe
```

- Replace `1234` with a secure password of your choice.
- The database will be available at `localhost:1521`.

#### Step 3: Verify the Container is Running

Run the following command to check if the container is running:

```bash
docker ps
```

You should see the `oracle-xe` container listed with port `1521` exposed.

#### Step 4: Connect to the Oracle Database and Create the User

Use a database client (e.g., **SQL Developer**, **IntelliJ Database Tool**, etc.) or a terminal to connect to the database:

1. **Connection details**:
  - **Host**: `localhost`
  - **Port**: `1521`
  - **Database/Service Name**: `XEPDB1`
  - **Username**: `SYSTEM`
  - **Password**: `1234` (or the password set in Step 2)

2. Once connected, execute the following SQL commands to create the required application user:

```sql
CREATE USER movie_app IDENTIFIED BY 1234;
GRANT CONNECT, RESOURCE TO movie_app;
ALTER USER movie_app QUOTA UNLIMITED ON USERS;
```

Replace `1234` with the desired user password. Ensure this matches your application's configuration file (`application.yml`).

---
### Stopping and Managing the Docker Container

To stop the Oracle container:

```bash
docker stop oracle-xe
```

To restart the container:

```bash
docker start oracle-xe
```

To remove the container, use the following commands (note: this will delete your database):

```bash
docker stop oracle-xe
docker rm oracle-xe
```
---

### Running the Application

1. Ensure the Oracle container is running:

```bash
docker start oracle-xe
```

2. Update the database configurations in the `application.yml` file:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1521/xe
    username: movie_app
    password: 1234
    driver-class-name: oracle.jdbc.OracleDriver
```

3. Run the application using Maven or a supported IDE:

```bash
./mvnw spring-boot:run
```

Flyway will automatically set up the database schema and tables when the application starts.

---

## Database Schema and Tables

The application uses **Flyway** to manage database migrations. When the application starts, Flyway will create the required schema and tables automatically. Key tables include:

- **Users**: User data, including roles and credentials.
- **Movies**: Movie records fetched from OMDB or added by admins.
- **Ratings**: User ratings for movies.

For custom schema modifications or migrations, refer to the `resources/db/migration` folder in the project directory.

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
        "Search": [
            {
                "imdbID": "tt14926832",
                "Title": "Anderson Spider Silva",
                "Year": "2023",
                "Type": "series",
                "Poster": "https://m.media-amazon.com/images/M/MV5BNjQ0ZDZkNmUtMjRiZC00OTQ0LWIyYjQtMWI5ZTYyZWIxNjhkXkEyXkFqcGc@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt2534642",
                "Title": "Captain Morten and the Spider Queen",
                "Year": "2018",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BYjdiMmRjNjgtMDFhMi00OTMyLThlYzgtNmFhOTk4NzA5ZmVhXkEyXkFqcGc@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt2345761",
                "Title": "Li'l Spider Girl",
                "Year": "2012",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BYjRmZTAyYTItNGExNy00ZTI5LTk5ODEtN2Y4MDBkYzA0YmNlXkEyXkFqcGc@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt0067934",
                "Title": "The Legend of Spider Forest",
                "Year": "1971",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BMDRlYzZjOTEtOWEzYy00NjE2LWI0NjMtMjU1NjQzN2ZjMWMzXkEyXkFqcGdeQXVyMTQ2MjQyNDc@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt1687220",
                "Title": "The Vampire Spider",
                "Year": "2012",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BYmQ4YWI4YWItZDE4NS00MjFkLWEyZTgtY2ViZTQ1YWIzMGQyXkEyXkFqcGdeQXVyNTEwMjA1NTU@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt0354378",
                "Title": "And Along Came a Spider",
                "Year": "2003",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BYThhYTllMzUtMzQ0Yy00M2I1LTg5NzgtOGU4MDRiMjZkOTc0XkEyXkFqcGdeQXVyMTI1MTg1NzMz._V1_SX300.jpg"
            },
            {
                "imdbID": "tt0420574",
                "Title": "De Superman à Spider-Man: L'aventure des super-héros",
                "Year": "2001",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BYjE4NmFhNjMtMjFkZS00MTE0LWI5MGUtYTdjMDBjOGU2NjRlXkEyXkFqcGc@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt0813896",
                "Title": "Spider Riders",
                "Year": "2005–2007",
                "Type": "series",
                "Poster": "https://m.media-amazon.com/images/M/MV5BMTU5OTgxMjYxNV5BMl5BanBnXkFtZTgwNjU5NzIxMzE@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt0163040",
                "Title": "The Spider and the Tulip",
                "Year": "1943",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BMjZkZTQ5OTEtYzJlZi00ZWNhLWJjYzItN2Q1YTliZGNmNzU0XkEyXkFqcGdeQXVyNjkxOTM4ODY@._V1_SX300.jpg"
            },
            {
                "imdbID": "tt20425026",
                "Title": "Spider-Man: All Roads Lead to No Way Home",
                "Year": "2022",
                "Type": "movie",
                "Poster": "https://m.media-amazon.com/images/M/MV5BODJlNzRlMDMtNWJkZS00OGFiLThjYmMtOWZjZTBjYjdhYmFlXkEyXkFqcGc@._V1_SX300.jpg"
            }
        ],
        "totalResults": "765",
        "Response": "True",
        "Error": null
    },
    "status": true,
    "currentDate": "2025-03-09T11:40:05.486Z"
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
        "Year": "1989",
        "Genre": "Action, Thriller",
        "Director": "Tao Dong, Shuhuang Zhong",
        "Writer": "Zuxun Ye, Lequn Zhang",
        "Actors": "Jan-Ching Do, Xiaoyan Li, Xinghuo Zhong",
        "Plot": "Set in the early republican period of China, a congressman was killed and a diamond necklace was stolen. Two investigator are arranged to find the murderer and necklace.",
        "Language": "Mandarin",
        "Poster": "https://m.media-amazon.com/images/M/MV5BMDRjYzcwODMtMTFjOC00YTA0LTg3ZGMtMzZlNGQzMWZkMjE4XkEyXkFqcGdeQXVyNzI1NzMxNzM@._V1_SX300.jpg",
        "Type": "movie",
        "averageRating": 3.0,
        "Response": null
    },
    "status": true,
    "currentDate": "2025-03-08T20:57:53.165Z"
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

---

## Database Schema and Tables

This project uses Flyway for database migrations. The schema includes:
- **`users`**: Stores user details.
- **`user_roles`**: Stores roles (`ROLE_ADMIN` and `ROLE_USER`).
- **`movies`**: Stores added movies with ratings.
- **`ratings`**: Stores user ratings for movies.

For details about migrations, see the `sql` files under `resources/db/migration`.


