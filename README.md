# LoungeBar Management System

## Description

This project is a web application designed to streamline lounge bar operations by managing real-time table bookings, 
product inventory, and customer orders. The platform features distinct interfaces for customers to book and
review service, and for staff to monitor sales and performance analytics.

---

## Technologies Adopted

- Backend: **Java** (Maven-based)
- Database: **PostgreSQL**
- Containerization: **Docker** 
- Web Services: **Tomcat**


## Project Structure

```
loungebar/
|
├── data/
|
├── src/main/
|       |
|       ├── database/
|       |       ├── population.sql
|       |       ├── schema.sql
|       |       ├── trigger.sql
|       |       
|       ├── java/dei/unipd/it/
|       |       ├── database/        # contains all the DAOs
|       |       ├── filter/          # filter for protected resources
|       |       ├── resources/       # record java classes representing database entities
|       |       ├── rest/            # REST resources classes
|       |       ├── servlets/        # servelts running back-end mechanisms
|       |
|       ├── resouces/
|       |       ├── log4j.xml
|       |
|       ├── webapp/
|              ├── js/         # JavScript files
|              ├── jsp/        # JSP pages
|              ├── META-INF/
|              |    ├── context.xml  
|              |
|              ├── WEB-INF/
|              |    ├── web.xml 
|
├── target/
├── tmp/        # tomcat configuration files
|     ├── context.xml
|     ├── server.xml
|     ├── tomcat-users.xml
|    
├── docker-compose.yml
├── pom.xml
```

## Requirements

Make sure you have installed:

- Docker
- Docker Compose
- _(Optional)_  Java and Maven, for local execution


## Running the Project

1. Clone the repository:
   ```bash
       git clone <repo-url>
       cd loungebar
   ```

2. Start the containers:
    ```bash
      docker compose up
   ```

Docker will automatically compile and deploy your application, as if you run:
    ```
      mvn clean compile package
    ```

