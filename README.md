
# Thomas Cook - Hotel Booking Portal

## Project Overview

The Hotel Booking Portal is a full-stack web application developed as part of the Thomas Cook Full Stack Development Interview Assignment.

It allows customers to browse hotels, search by location, view room details, book rooms, retrieve booking details and cancel reservations.

## Technology Stack

### Frontend
- Angular 20
- TypeScript
- HTML
- CSS

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs

### Database
- MySQL

## Project Structure

```text
ThomasCook_HotelBooking_Portal/
|
|-- hotelbooking/
|   |-- src/
|   |-- pom.xml
|   |-- DESIGN.md
|
|-- hotel-booking-frontend/
|   |-- src/
|   |-- package.json
|   |-- README.md
|
|-- README.md
```

## Main Features

- Browse hotels and rooms.
- Search hotels by location.
- View room prices and capacity.
- Book hotel rooms.
- Calculate the total booking amount.
- Generate unique booking references.
- Retrieve bookings using reference and email.
- Cancel bookings.
- Validate booking dates.
- Check overlapping confirmed bookings against room capacity.

## How to Run the Project

### 1. Start MySQL

Open MySQL Workbench and make sure the MySQL server is running.

### 2. Start Backend

Open the `hotelbooking` project in Eclipse.

Run `HotelbookingApplication.java`.

Backend URL:

http://localhost:8080

### 3. Start Frontend

Open `hotel-booking-frontend` in VS Code.

Run:

```bash
npm install
npx ng serve
```

Open:

http://localhost:4200

## Documentation

- Frontend instructions: `hotel-booking-frontend/README.md`
- System architecture and database design: `hotelbooking/DESIGN.md`

## Author

Thomas Cook Full Stack Development Interview Assignment.
