
package com.thomascook.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thomascook.hotelbooking.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
