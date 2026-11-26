
package com.thomascook.hotelbooking.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thomascook.hotelbooking.entity.Booking;
import com.thomascook.hotelbooking.repository.BookingRepository;
import com.thomascook.hotelbooking.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public BookingController(
            BookingService bookingService,
            BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    // Create booking
    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {

        return bookingService.createBooking(
                request.getCustomerName(),
                request.getEmail(),
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut()
        );
    }

    // Retrieve booking using reference and email
    @GetMapping("/{reference}")
    public ResponseEntity<Booking> getBooking(
            @PathVariable String reference,
            @RequestParam String email) {

        return bookingRepository
                .findByBookingReferenceAndEmail(reference, email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cancel booking
    @DeleteMapping("/{reference}")
    public ResponseEntity<String> cancelBooking(
            @PathVariable String reference,
            @RequestParam String email) {

        return bookingRepository
                .findByBookingReferenceAndEmail(reference, email)
                .map(booking -> {
                    booking.setStatus("CANCELLED");
                    bookingRepository.save(booking);
                    return ResponseEntity.ok("Booking cancelled successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Request data class
    public static class BookingRequest {

        private String customerName;
        private String email;
        private Long roomId;
        private LocalDate checkIn;
        private LocalDate checkOut;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Long getRoomId() {
            return roomId;
        }

        public void setRoomId(Long roomId) {
            this.roomId = roomId;
        }

        public LocalDate getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(LocalDate checkIn) {
            this.checkIn = checkIn;
        }

        public LocalDate getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(LocalDate checkOut) {
            this.checkOut = checkOut;
        }
    }
}
