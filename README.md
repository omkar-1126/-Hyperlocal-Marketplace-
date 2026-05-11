# Hyperlocal Marketplace

A hyperlocal freelancer marketplace web application built with Spring Boot and Thymeleaf. Clients can browse and book local service providers (tutors, plumbers, electricians), and freelancers can manage their appointments.

## Features

- Client and Freelancer registration & login (session-based auth)
- Browse freelancers by service type (Tutor, Plumber, Electrician)
- Book appointments with time-slot conflict detection
- Freelancer dashboard with booking management (Accept / Reject)
- **Reviews** — clients can write, edit, and delete reviews on freelancer profiles
- **Freelancer phone number** revealed to the client only after a booking is accepted
- **Customer home address** revealed to the freelancer only after they accept the booking

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.3.5 |
| Frontend | Thymeleaf, Bootstrap 5 |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security (session-based) |
| Build | Maven |

## Prerequisites

- Java 17+
- MySQL 8 running on `localhost:3306`
- Maven (or use the Eclipse Spring Boot runner)

## Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/omkar-1126/-Hyperlocal-Marketplace-.git
   cd -Hyperlocal-Marketplace-
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE IF NOT EXISTS hyperlocal_db;
   ```

3. **Configure credentials** — edit `src/main/resources/application.properties` if your MySQL password differs from the default:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=mysql123
   ```

4. **Run the app**
   - In Eclipse: Right-click project → **Run As → Spring Boot App**
   - Or via Maven:
     ```bash
     mvn spring-boot:run
     ```

5. Open `http://localhost:8080` in your browser.

> Tables are created/updated automatically via `spring.jpa.hibernate.ddl-auto=update` — no manual SQL migrations needed.

## Project Structure

```
src/main/java/com/hyperlocal/marketplace/
├── config/          # Spring Security config
├── controller/      # AuthController, BookingController, FreelancerController,
│                    # ClientController, ServiceController, ReviewController
├── model/           # User, ServiceProvider, Appointment, Booking, Review
└── repository/      # JPA repositories

src/main/resources/templates/
├── home, login, register-client, register-freelancer
├── client-dashboard, client-bookings
├── freelancer-dashboard, freelancer-bookings, freelancer-profile
├── view-services, book, reviews, edit-review, success
```

## Contributor

- **omkar-1126** — [@omkar-1126](https://github.com/omkar-1126)

## License

This project is licensed under the [MIT License](LICENSE).
