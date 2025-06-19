# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java training project for service member management system, built using Spring Boot 3 with Java 17. The project demonstrates implementation of member and charge management features with security integration.

## Architecture

**Multi-module Maven Project:**
- `webapp/` - Spring Boot web application (WAR packaging)
- `batch/` - Spring Boot batch processing application (JAR packaging)

**Technology Stack:**
- Spring Boot 3.4.4 with Spring Framework 6
- Spring Web + Thymeleaf for web UI
- Spring Security for authentication
- Spring Data JPA with H2/PostgreSQL
- Lombok for code generation
- Maven for build management

**Authentication:**
- Username: `user`
- Password: `password`
- In-memory authentication configured in SecurityConfig

## Common Development Commands

**Run the web application:**
```bash
./mvnw spring-boot:run -pl webapp
```

**Run the batch application:**
```bash
./mvnw spring-boot:run -pl batch
```

**Build entire project:**
```bash
./mvnw package
```

**Run tests:**
```bash
./mvnw test
```

**Run specific module tests:**
```bash
./mvnw test -pl webapp
./mvnw test -pl batch
```

## Project Structure

**Web Application (`webapp/`):**
- Controllers: `com.s_giken.training.webapp.controller`
- Services: `com.s_giken.training.webapp.service` (with implementation classes)
- Repositories: `com.s_giken.training.webapp.repository`
- Entities: `com.s_giken.training.webapp.model.entity`
- Templates: `src/main/resources/templates/` (Thymeleaf)
- Security: `com.s_giken.training.webapp.config.SecurityConfig`

**Batch Application (`batch/`):**
- Main class implements `CommandLineRunner`
- Uses `JdbcTemplate` for database operations
- Processes billing data with transaction support

## Database Configuration

- Development profile active by default (`spring.profiles.active=dev`)
- H2 database for development (in-memory)
- PostgreSQL driver included for production
- Database schema defined in `schema.sql`
- H2 Console available at `/h2-console` during development

## Coding Standards

This project follows Google Java Style Guide with customizations:
- 2-space indentation (no tabs)
- 100-character line limit
- K&R brace style
- UTF-8 encoding
- Google Java Style Eclipse formatter configuration available in `extra/eclipse-java-google-style-custom.xml`

**Key conventions:**
- Use `lowerCamelCase` for methods, fields, variables
- Use `UpperCamelCase` for classes
- Use `CONSTANT_CASE` for static final constants
- Always use `@Override` annotation
- Never ignore caught exceptions without comment
- One variable declaration per line

## Development Notes

**Testing:**
- Basic test structure in place but minimal test coverage
- Test classes follow naming convention: `{ClassUnderTest}Tests`

**Security:**
- CSRF disabled for development
- H2 console access permitted
- Form-based login with default success redirect to root

**Batch Processing:**
- Expects command line argument in YYYYMM format
- Processes billing data with transactional integrity
- Includes data cleanup and billing status management