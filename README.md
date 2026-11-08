# Like Hero To Zero

A web application for exploring country-level CO₂ emissions and their historical development.

## Academic Context

This project was developed as part of the **IPWA02-01** course at **IU International University of Applied Sciences**.

## Project Overview

Like Hero To Zero provides an overview of reported CO₂ emissions by country. Users can select a country, explore its latest available emission data and view historical emission trends.

The application also provides a protected researcher area for managing emission records.

## Features

- Country selection and emission overview
- Display of the latest available CO₂ emission values
- Historical emission trends using interactive charts
- Researcher authentication
- Management of emission records
- Persistent data storage using MySQL

## Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java 21 |
| Framework | Spring Boot 4.1.2 |
| Web | Spring MVC |
| Template Engine | Thymeleaf |
| Database | MySQL |
| Persistence | Spring Data JPA / Hibernate |
| Security | Spring Security |
| Frontend | HTML, CSS, JavaScript |
| CSS Framework | Bootstrap 5.3.3 |
| Charts | Chart.js 4.4.8 |
| Build Tool | Maven Wrapper |

## Application Structure

The Java source code follows a package-based structure:

```text
iu.ipwa02.likeherotozero
├── config
├── controller
├── dto
├── entity
├── repository
├── service
└── LikeHeroToZeroApplication.java
```

## Prerequisites

Before running the application, make sure the following software is installed:

- Java Development Kit (JDK) 21
- MySQL Server 8.4
- Git

An IDE such as IntelliJ IDEA is recommended.

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/max-greis/like-hero-to-zero.git
cd like-hero-to-zero
```

### 2. Create the Database

Create the database and a dedicated application user in MySQL:

```sql
CREATE DATABASE like_hero_to_zero;

CREATE USER 'app_user'@'localhost'
IDENTIFIED BY 'your_database_password';

GRANT ALL PRIVILEGES ON like_hero_to_zero.*
TO 'app_user'@'localhost';
```

Replace `your_database_password` with a password of your choice.

If the database or user already exists, use the existing objects instead of creating them again.

### 3. Configure Environment Variables

The application reads its database password from an environment variable.

The variable is referenced in `application.properties`:

```properties
spring.datasource.password=${DB_PASSWORD}
```

The actual password is intentionally not stored in the repository.

**IntelliJ IDEA**

1. Open the project in IntelliJ IDEA.
2. Open Run → Edit Configurations.
3. Select the `LikeHeroToZeroApplication` run configuration.
4. Locate the Environment variables field.
5. Add the following variable:

```text
DB_PASSWORD=your_database_password
```

6. Save the configuration.

**Windows PowerShell**

Alternatively, set the variable temporarily in PowerShell:

```powershell
$env:DB_PASSWORD="your_database_password"
```

This setting applies to the current PowerShell session and processes started from it.

### 4. Start the Application

The project includes the Maven Wrapper, so a separate Maven installation is not required.

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

Alternatively, start `LikeHeroToZeroApplication` directly from IntelliJ IDEA.

### 5. Open the Application

Once Spring Boot has started successfully, open:

```text
http://localhost:8080
```

The database schema is managed through Hibernate with the configured `ddl-auto=update` setting.

## Database Configuration

The database connection is configured in:

```text
src/main/resources/application.properties
```

Current local development settings:

| Property | Value |
|---|---|
| Database | like_hero_to_zero |
| Username | app_user |
| Port | 3306 |
| Password | Environment variable `DB_PASSWORD` |
| Hibernate DDL | update |

## Security Notes

- Database credentials are supplied through environment variables.
- Actual passwords must not be committed to Git.
- The researcher area is protected using Spring Security.

## Project Status

Academic project developed as part of the IU IPWA02-01 course.

## Emission Data

The application uses country-level emission data from the
World Bank's World Development Indicators.

- Source: World Bank
- Indicator: EN.GHG.CO2.MT.CE.AR5
- Unit in source data: Mt CO2e
- Unit in application: kt CO2e
- Conversion: 1 Mt = 1,000 kt
- Data period: 2020–2024

The current development dataset contains five countries:
Germany, United States, China, India and Brazil.

Source:
https://data.worldbank.org/indicator/EN.GHG.CO2.MT.CE.AR5