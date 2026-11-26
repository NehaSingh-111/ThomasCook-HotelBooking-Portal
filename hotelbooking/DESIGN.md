
# Hotel Booking Portal - Design Document

## 1. Project Overview

The Hotel Booking Portal is a full-stack web application developed for the Thomas Cook Full Stack Development Interview Assignment.

The application allows users to browse hotels, view available room types, book rooms, retrieve booking details and cancel bookings.

## 2. Technology Stack

### Frontend
- Angular
- HTML
- CSS
- TypeScript

### Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate

### Database
- MySQL

## 3. System Architecture

The application follows a three-layer architecture.

1. Presentation Layer: Angular provides the user interface.
2. Application Layer: Spring Boot REST APIs handle business logic and requests.
3. Data Layer: MySQL stores hotel, room and booking information.

Angular communicates with Spring Boot through HTTP requests using REST APIs.

## 4. Database Design

The application uses three main tables.

### Hotel
- id (Primary Key)
- name
- location
- description
- amenities

### Room
- id (Primary Key)
- room_type
- price
- capacity
- hotel_id (Foreign Key)

### Booking
- id (Primary Key)
- booking_reference (Unique)
- customer_name
- email
- check_in
- check_out
- total_amount
- status
- room_id (Foreign Key)

### Relationships
- One hotel can have multiple rooms.
- One room can have multiple booking records.
- Each booking belongs to one room.

## 5. REST API Design

### Hotel APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | /api/hotels | Get all hotels |
| GET | /api/hotels/{id} | Get hotel by ID |
| GET | /api/hotels/search?location=Mumbai | Search hotels by location |
| POST | /api/hotels | Add a hotel |

### Room API

| Method | Endpoint | Purpose |
|---|---|---|
| GET | /api/rooms?hotelId=1 | Get rooms for a hotel |

### Booking APIs

| Method | Endpoint | Purpose |
|---|---|---|
| POST | /api/bookings | Create a booking |
| GET | /api/bookings/{reference}?email=email | Retrieve a booking |
| DELETE | /api/bookings/{reference}?email=email | Cancel a booking |

## 6. Booking Logic

1. The customer selects a hotel and room.
2. The customer enters their name, email and travel dates.
3. The backend validates the booking information.
4. Check-in cannot be in the past.
5. Check-out must be after check-in.
6. The backend checks overlapping confirmed bookings against room capacity.
7. The total amount is calculated by multiplying the room price by the number of nights.
8. A unique booking reference is generated.
9. The booking is saved with CONFIRMED status.

## 7. Room Availability and Overlapping Bookings

The application checks overlapping bookings using the following conditions:

- Existing check-in is before the requested check-out.
- Existing check-out is after the requested check-in.
- Only CONFIRMED bookings are counted.

A cancelled booking does not occupy room capacity.

A database pessimistic write lock is used when retrieving a room for booking to help prevent simultaneous bookings from exceeding capacity.

Checkout and the next guest's check-in can occur on the same date.

## 8. Cancellation

Customers can retrieve their booking using the booking reference and email address.

When a booking is cancelled, its status changes to CANCELLED.

Cancelled bookings are excluded from future availability calculations.

## 9. Error Handling

A global exception handler provides consistent error responses.

- HTTP 400: Invalid booking details.
- HTTP 500: Unexpected server errors.

## 10. Assumptions and Limitations

- Room capacity represents the number of rooms available for each room type.
- Each booking reserves one room.
- Booking references are generated using UUID values.
- The application does not include online payment.
- User authentication is not implemented.
- Hotel and room data are stored in MySQL.
- Location search currently uses an exact location match.
- Date-based hotel search and a separate room availability API are not yet implemented.
- Automated concurrency testing is still required.

## 11. Future Enhancements

- Online payment integration.
- User registration and login.
- Advanced hotel and date-based search.
- Improved room availability display.
- Admin dashboard for managing hotels and bookings.
- Email booking confirmation.

## 12. Design Decisions and Trade-offs

- Angular was selected for building a structured and interactive frontend.
- Java Spring Boot was selected for developing REST APIs and handling business logic.
- MySQL was selected for storing structured hotel, room and booking information.
- Spring Data JPA was used to simplify database operations.
- Booking references are generated using UUID values to provide unique booking identification.
- A three-layer architecture was selected to separate frontend, business logic and database responsibilities.

### Trade-offs

- Location search currently uses exact location matching instead of advanced search.
- Authentication and online payment were not included to keep the implementation focused on the assignment requirements.
- Room availability is checked through backend booking logic rather than a dedicated availability calendar.

## 13. Handling Simultaneous Bookings

The application uses database pessimistic write locking when retrieving a room for booking.

The booking process is transactional.

1. A customer selects a room and booking dates.
2. The backend obtains a pessimistic write lock on the selected room.
3. It checks the number of overlapping confirmed bookings.
4. It compares the booking count with the room capacity.
5. If capacity is available, the booking is saved.
6. Another transaction attempting to lock the same room must wait until the first transaction completes.
7. The next transaction then checks the booking count before proceeding.

This approach helps prevent simultaneous requests from exceeding the available room capacity.

Further concurrent-load testing would be useful to verify this behaviour.

## 14. Surprise Feature - Self-Service Booking Management

An additional feature implemented in the application is self-service booking management.

Customers can:

- Retrieve