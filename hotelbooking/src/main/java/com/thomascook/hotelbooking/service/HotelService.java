
package com.thomascook.hotelbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.thomascook.hotelbooking.entity.Hotel;
import com.thomascook.hotelbooking.repository.HotelRepository;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // Get all hotels
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Get hotel by ID
    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    // Search hotels by location
    public List<Hotel> searchHotels(String location) {
        return hotelRepository.findAll()
                .stream()
                .filter(h -> h.getLocation() != null &&
                        h.getLocation().equalsIgnoreCase(location))
                .toList();
    }

    // Save hotel
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
