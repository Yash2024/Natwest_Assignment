# Report Generation Service

This is a Java Spring Boot service that ingests various file formats and generates transformed reports based on configurable transformation rules. The service handles large files efficiently and allows triggering via REST API.

## Features

- Upload CSV files for input and reference data.
- Process and store input and reference data in batches.
- Generate output reports by joining input and reference data.
- Schedule report generation using configurable cron expressions.
- Download the generated report as a CSV file.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Apache Commons CSV
- OpenCSV
- JUnit 5
- Mockito

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- PostgreSQL (or any other database supported by Spring Data JPA)

### Installation

1. Clone the repository:

    ```sh
    git clone https://github.com/your-username/report-gen-service.git
    cd report-gen-service
    ```

2. Configure the database:

    Update the `application.properties` file with your database settings:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```

3. Build the project:

    ```sh
    mvn clean install
    ```

4. Run the application:

    ```sh
    mvn spring-boot:run
    ```

### API Endpoints

#### Upload CSV Files

- **URL:** `/api/upload`
- **Method:** `POST`
- **Parameters:**
  - `file1` (multipart file): Input data CSV file.
  - `file2` (multipart file): Reference data CSV file.

#### Generate and Download Report

- **URL:** `/api/output`
- **Method:** `GET`
- **Response:** CSV file containing the generated report.

### Scheduling Report Generation

The application includes a scheduled task that generates reports based on a configurable cron expression. By default, it runs every day at midnight.

You can update the cron expression in the `application.properties` file:

```properties
schedule.cron.expression=0 0 0 * * *
