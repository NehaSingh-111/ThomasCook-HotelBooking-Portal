package com.thomascook.hotelbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomascook.hotelbooking.entity.Booking;
import com.thomascook.hotelbooking.entity.Room;
import com.thomascook.hotelbooking.repository.BookingRepository;
import com.thomascook.hotelbooking.repository.RoomRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(
            BookingRepository bookingRepository,
            RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Booking createBooking(
            String name,
            String email,
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Please select booking dates");
        }

        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in cannot be in the past");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        if (roomId == null) {
            throw new IllegalArgumentException("Please select a room");
        }

        Room room = roomRepository.findByIdForUpdate(roomId)
        	    .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        long bookedRooms = bookingRepository.countOverlappingBookings(
                roomId, checkIn, checkOut);

        if (bookedRooms >= room.getCapacity()) {
            throw new IllegalArgumentException(
                    "Sorry! No rooms are available for these dates");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        BigDecimal total = BigDecimal.valueOf(room.getPrice())
                .multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();

        booking.setBookingReference(
                UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        booking.setCustomerName(name);
        booking.setEmail(email);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setTotalAmount(total.doubleValue());
        booking.setStatus("CONFIRMED");
        booking.setRoom(room);

        return bookingRepository.save(booking);
    }
}
