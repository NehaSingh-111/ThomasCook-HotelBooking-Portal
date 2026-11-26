
# Thomas Cook - Hotel Booking Portal

## Project Overview

This is the frontend of the Thomas Cook Hotel Booking Portal, developed using Angular.

It allows customers to browse hotels, search by location, view room details, book rooms, retrieve booking information and cancel reservations.

## Technologies Used

- Angular 20
- TypeScript
- HTML5
- CSS3
- Angular Forms
- Angular HttpClient

## Features

- Display hotel listings.
- Search hotels by location.
- View hotel details and room types.
- Select a room for booking.
- Enter customer details and travel dates.
- Display booking price calculation.
- Confirm hotel bookings.
- Retrieve bookings using booking reference and email.
- Cancel bookings.

## Backend Integration

The frontend communicates with a Java Spring Boot backend through REST APIs.

Backend URL:

http://localhost:8080

## Prerequisites

- Node.js
- npm
- Angular CLI

## Installation

Open the frontend project folder in VS Code and run:

```bash
npm install
```

## Development Server

Start the Angular application:

```bash
npx ng serve
```

Open your browser:

http://localhost:4200/

## Build

To create a production build:

```bash
npx ng build
```

## Backend Requirement

The Spring Boot backend and MySQL database must be running for hotel data and booking operations to work.

## Related Documentation

Refer to the backend project's README.md and DESIGN.md for backend setup, database design, API details and booking logic.

## Assignment

Developed as part of the Thomas Cook Full Stack Development Interview Assignment.
