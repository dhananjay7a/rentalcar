# Car Rental REST API

This project is a **Car Rental System** built with Java and Spring Boot. It provides essential functionalities for managing users, cars, bookings, and payments. The system includes custom exception handling, user authentication, and a well-structured service layer for easy maintenance.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [API Endpoints](#api-endpoints)
- [Custom Exception Handling](#custom-exception-handling)
- [Future Enhancements](#future-enhancements)

## Features

- **User Management**: Register, log in, and view rental history.
- **Car Management**: Admins can add, edit, and delete car listings.
- **Booking System**: Users can rent cars by booking available cars.
- **Service Layer**: Structured service layer for maintainability.

## Technologies Used

- Java (Core and Advanced)
- Spring Boot (REST API Framework)
- JPA/Hibernate (Data Persistence)
- MySQL (Database)
- Servlets (Backend Processing)
- Maven (Build Automation)
- JUnit (For Unit Testing)

## Installation

### Prerequisites

- JDK 11 or later
- MySQL installed and running
- Maven installed
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Steps to Setup

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/your-username/car-rental-api.git
    cd car-rental-api
    ```

2. **Configure the Database**:

    Open the `application.properties` file and set up your MySQL connection:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/car_rental
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Build the Project**:
    ```bash
    mvn clean install
    ```

4. **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```

5. **Access the API**:
    The API will be accessible at `http://localhost:8080/api`.

## API Endpoints

### User Endpoints

| Method | Endpoint               | Description                   |
|--------|------------------------|-------------------------------|
| POST   | `/api/users/register`   | Register a new user           |
| POST   | `/api/users/login`      | Log in a user                 |
| GET    | `/api/users/{id}`       | Get user by ID                |

### Car Endpoints

| Method | Endpoint                                 | Description                   |
|--------|------------------------------------------|-------------------------------|
| GET    | `/api/cars`                              | Get all available cars        |
| POST   | `/api/cars/rent/{userId}/{carId}`        | Rent a car (user)         |
| PUT    | `/api/cars/return/{user-id}/{car-id}`    |Return a car (user)    |

### Admin Endpoints

| Method | Endpoint                   | Description                   |
|--------|----------------------------|-------------------------------|
| Post    | `/api/admin/login`        |  Login a Admin     |
| POST   | `/api/admin/cars`          | Add a new car (Admin)         |
| PUT    | `/api/cars/update/{id}`    | Update car details (Admin)    |
| DELETE | `/api/cars/delete/{id}`    | Delete a car (Admin)          |



## In future i will add Custom Exception Handling

The project uses custom exceptions to handle specific scenarios like:

- **UserNotFoundException**: Thrown when a user is not found in the system.
- **CarNotAvailableException**: Thrown when a car is not available for booking.
- **BookingNotFoundException**: Thrown when a booking ID is invalid.

## Future Enhancements
- **Frontend Implementation**: Build a user-friendly frontend using React or Angular.
- **Enhanced Security**: Implement JWT-based authentication.
- **Payment Integration**: Integrate with real-world payment gateways (e.g., Stripe, PayPal).
- **Advanced Booking Features**: Add features like booking modification, advanced filters for cars.

