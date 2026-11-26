package com.thomascook.hotelbooking.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thomascook.hotelbooking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingReferenceAndEmail(
            String bookingReference, String email);

    @Query("""
        SELECT COUNT(b)
        FROM Booking b
        WHERE b.room.id = :roomId
        AND b.status = 'CONFIRMED'
        AND b.checkIn < :checkOut
        AND b.checkOut > :checkIn
    """)
    long countOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);
}
