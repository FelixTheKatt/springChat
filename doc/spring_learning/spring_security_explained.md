# Spring Security Explained

## Introduction
This document explains the security setup in our Spring Boot application, even for beginners.

## What We Implemented
We integrated **Spring Security** with **JWT authentication** and **user role-based access**. The key components include:

### 1. **User Authentication with Spring Security**
- We set up `UserDetailsService` to load users from the database.
- Passwords are stored securely using **BCrypt**.
- Authentication is handled by **UsernamePasswordAuthenticationToken**.

### 2. **JWT (JSON Web Token) for Authentication**
- When a user logs in, we generate a JWT token.
- The token is signed and contains user information.
- On every request, the token is validated to allow access.

### 3. **Security Configuration (SecurityConfig.java)**
- Defines what endpoints require authentication.
- Configures JWT authentication filter.
- Allows some requests (e.g., login) to be accessed without authentication.

### 4. **Data Initialization (DataInitializer.java)**
- Creates an `admin` user with a hashed password.
- Ensures that the database has initial users to test authentication.

### 5. **Using Postman to Test**
1. **Login**: Send a `POST` request to `/api/auth/login` with credentials.
2. **Get JWT Token**: Copy the received JWT.
3. **Access Protected Routes**: Send requests with `Authorization: Bearer <TOKEN>` header.

## Summary
- **Users must authenticate** with a username (pseudo) and password.
- **A JWT token is issued** after successful authentication.
- **Protected routes require the JWT token** to be accessed.

### Next Steps?
- Implement **role-based access** (admin vs user permissions).
- Add **token expiration handling**.
- Enhance security logs.

Happy Coding! 🚀