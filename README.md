# Google URL Shortening Service

This project provides a simple web service for shortening URLs using the Google URL Shortener API. It is built with Java, using the Apache HttpClient library for making HTTP requests and Gson for JSON processing. The web service is implemented with Spring Boot, allowing easy deployment and scalability.

## Features

- Shorten long URLs using the Google URL Shortener API.
- Retrieve the original long URL from a shortened URL.
- RESTful API endpoints for URL shortening and expanding.
- Exception handling for common errors.

## Prerequisites

- Java 8 or later
- Maven
- Google API Key for the URL Shortener API (deprecated, but used for example purposes)

## Setup

1. **Clone the repository**:
    ```sh
    git clone https://github.com/<your-username>/google-url-shortening-service.git
    cd google-url-shortening-service
    ```

2. **Configure your Google API Key**:
   - Replace `<google-app-api-key>` in `GoogleURLShortening.java` with your Google API Key.

3. **Build the project**:
    ```sh
    mvn clean install
    ```

## Running the Application

You can run the Spring Boot application using the following command:

```sh
mvn spring-boot:run
