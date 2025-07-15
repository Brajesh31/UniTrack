# UniTrack
An attendance system [Final year project]

Prerequisites

Java Development Kit (JDK) 17 or higher
Maven 3.8.x

Technologies Used

Spring Boot
Spring Data JPA
Spring Security
MySQL, PostgreSQL
Swagger/OpenAPI for documentation

Getting Started
Clone the Repository
git clone https://github.com/XanderJ1/UniTrack.git

# Configuration

Configure database settings in src/main/resources/application.properties:

# Postgres Configuration

spring.datasource.url=jdbc:mysql://localhost:3306/yourdbname
spring.datasource.username=yourusername
spring.datasource.password=yourpassword


# MySQL Configuration

**Database Configuration** 
spring.datasource.url=jdbc:mysql://localhost:3306/unitrack
spring.datasource.username=root
spring.datasource.password=password

MySQL Driver

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect


**Set up environment variables (Optional)**

export DB_USERNAME=yourusername
export DB_PASSWORD=yourpassword
Build the Application
Using Maven
mvn clean install


# Run

 **Skip tests if needed**

mvn clean install -DskipTests
Run the Application
Run directly
mvn spring-boot:run

# Or using Java
java -jar target/your-application-name.jar


# API Documentation

Follow the link below
https://documenter.getpostman.com/view/39180074/2sAYkLkwCQ

# OR
Access Swagger UI:

Local: http://localhost:8080/swagger-ui.html
Provides interactive API documentation and testing

Access Swagger JSON
http://localhost:8081/v3/api-docs
