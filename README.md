# Movie Microservice Architecture

## Overview

This project implements a set of microservices related to movie information, ratings, and catalog management, with service discovery and registration using **Eureka Server**. The microservices include:

- **Rating Data Service**: Provides movie ratings from users.
- **Movie Catalog Service**: Maintains a catalog of movies, including details such as name, genre, director, and release year.
- **Movie Info Service**: Provides detailed information about movies, such as description, cast, and crew.
  
Eureka Server is used for service discovery and registration. This allows each service to register itself with Eureka, and other services can discover them without hard-coding URLs.

## Architecture

The system follows the **Microservices** architecture pattern with the following services:

1. **Eureka Server**: Acts as the service registry for microservices, enabling services to find each other dynamically.
2. **Movie Catalog Service**: Maintains a list of movies, and integrates with the Movie Rating Service to get ratings for each movie.
3. **Movie Info Service**: Provides detailed information for a specific movie, including its cast, crew, and synopsis.
4. **Rating Data Service**: Stores and manages the ratings given by users for each movie.

Each service is independent, has its own database (or internal storage), and communicates with other services via HTTP or REST API calls.

## Technology Stack

- **Spring Boot**: For building the microservices.
- **Spring Cloud Netflix Eureka**: For service discovery and registration.
- **Spring Web**: For RESTful communication between services.
- **Spring Data JPA** (Optional): For database access (could use an H2 in-memory database for simplicity).
- **PostgreSQL** (Optional): For a persistent database in production.
- **Docker**: For containerization and deployment.
- **Maven**: For building and managing dependencies.

## Setup and Configuration

### 1. Setting up Eureka Server

Eureka Server acts as the service registry where all microservices will register themselves. To start the Eureka server:

1. Clone or download the `eureka-server` repository.
2. Navigate to the project folder and run:

   ```bash
   mvn spring-boot:run
   ```

   Eureka Server will be available at: [http://localhost:8761](http://localhost:8761).

### 2. Movie Catalog Service

This microservice handles movie catalog management and integrates with Movie Rating and Movie Info services.

#### Configuration:
- Make sure the service registers itself with the Eureka Server by adding the following configuration in the `application.properties` or `application.yml`:

   ```properties
   eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
   spring.application.name=movie-catalog-service
   ```

#### Run the Service:

1. Clone or download the `movie-catalog-service` repository.
2. Navigate to the project folder and run:

   ```bash
   mvn spring-boot:run
   ```

   The Movie Catalog Service will be available at [http://localhost:8081](http://localhost:8081).

### 3. Movie Info Service

This microservice provides detailed information about movies, such as synopsis, cast, director, and year.

#### Configuration:
- The Movie Info Service registers itself with Eureka Server in a similar manner.

   ```properties
   eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
   spring.application.name=movie-info-service
   ```

#### Run the Service:

1. Clone or download the `movie-info-service` repository.
2. Navigate to the project folder and run:

   ```bash
   mvn spring-boot:run
   ```

   The Movie Info Service will be available at [http://localhost:8082](http://localhost:8082).

### 4. Movie Rating Service

This service manages movie ratings submitted by users, providing a rating for each movie.

#### Configuration:
- The Movie Rating Service registers itself with Eureka Server.

   ```properties
   eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
   spring.application.name=rating-data-service
   ```

#### Run the Service:

1. Clone or download the `rating-data-service` repository.
2. Navigate to the project folder and run:

   ```bash
   mvn spring-boot:run
   ```

   The Movie Rating Service will be available at [http://localhost:8083](http://localhost:8083).

---

## Communication Flow

1. **Movie Catalog Service** calls the **Movie Info Service** to get detailed information about movies.
2. **Movie Catalog Service** calls the **Movie Rating Service** to get ratings for each movie.
3. All services register with **Eureka Server** for service discovery.

The following endpoints are available for interaction:

### Movie Catalog Service API Endpoints:

- **GET /movies**: Fetch the list of all movies with basic details.
  
  Example response:
  ```json
  [
    {
      "movieId": "1",
      "name": "Inception",
      "genre": "Sci-Fi",
      "releaseYear": "2010"
    },
    {
      "movieId": "2",
      "name": "The Dark Knight",
      "genre": "Action",
      "releaseYear": "2008"
    }
  ]
  ```

- **GET /movies/{id}**: Get detailed movie information (from Movie Info Service) and movie ratings (from Movie Rating Service).

  Example response:
  ```json
  {
    "movieId": "1",
    "name": "Inception",
    "genre": "Sci-Fi",
    "releaseYear": "2010",
    "rating": 8.8,
    "synopsis": "A thief who steals corporate secrets through the use of dream-sharing technology...",
    "cast": ["Leonardo DiCaprio", "Joseph Gordon-Levitt", "Ellen Page"]
  }
  ```

### Movie Info Service API Endpoints:

- **GET /movies/{id}**: Fetch detailed information about a specific movie, such as synopsis, cast, and crew.

  Example response:
  ```json
  {
    "movieId": "1",
    "synopsis": "A thief who steals corporate secrets through the use of dream-sharing technology...",
    "cast": ["Leonardo DiCaprio", "Joseph Gordon-Levitt", "Ellen Page"],
    "director": "Christopher Nolan"
  }
  ```

### Movie Rating Service API Endpoints:

- **GET /movies/{id}/rating**: Fetch the rating of a specific movie.

  Example response:
  ```json
  {
    "movieId": "1",
    "rating": 8.8
  }
  ```

- **POST /movies/{id}/rating**: Submit a new rating for a specific movie.

  Request body:
  ```json
  {
    "rating": 9.0
  }
  ```

  Example response:
  ```json
  {
    "message": "Rating submitted successfully."
  }
  ```

---

## Containerization (Optional)

To run the services in isolated environments, you can containerize each service using Docker. Below is a basic example of how to containerize the services:

1. Create a `Dockerfile` in each service's project directory.
2. Build the Docker image:

   ```bash
   docker build -t movie-catalog-service .
   ```

3. Run the container:

   ```bash
   docker run -p 8081:8081 movie-catalog-service
   ```

Repeat this for each microservice.

---

## Conclusion

This microservice architecture enables scalability, flexibility, and maintainability for handling movie-related services, such as catalog management, ratings, and detailed information. The use of Eureka Server ensures easy service discovery and communication, making it easy to scale and manage microservices in a dynamic environment.

