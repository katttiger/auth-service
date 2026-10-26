# Auth service

## Overview

A microservice that uses JSON Web Tokens (JWT) to authenticate a user stored in the database. It is part of a larger
project to create a webshop (link below to the related programs and their github repositories)

## Technical specification

Techniques: Java JDK 17, Maven, Spring Boot Security, Spring Boot REST Controller, Spring Boot JPA Repository, JDBC.
Database: PostgreSQL on Neon.com.
Port: (localhost): 9000.
CI/CD Workflow: Java CI with Maven.

## Environment variables

- DB_PASSWORD (authentication to your database.)
- DB_USERNAME (authentication to your database.)
- DB_URL (authentication to your database. Remember to use JDBC when connecting.)
- JWT_KEY_ID (identifies the key used to sign token)
- JWT_PRIVATE_KEY (needed for the tests and build to work.)
- JWT_PUBLIC_KEY (needed for the tests and build to work.)

## Startup

**In the terminal**
Windows: mvnw.comd spring-boot:run
macOS: ./mvnw spring-boot:run

**IntelliJ IDE**

1. Download the repository from GitHub and launch it in the IDE.
2. Make sure that the dependencies in the pom.xml-file are loaded correctly and synchronized with the project.
3. Press the play button, making sure that the main method (AuthServiceApplication) is marked as the file being run.

## Related microservices

Product-service: https://github.com/Joajak92/product-service
Order-service: https://github.com/klasen98/projekt-order-service
E-mail-service: https://github.com/joel-schou/email-service

Gateway-service: https://github.com/joel-schou/gateway-service

Front-end: https://github.com/Joajak92/webshop-frontend