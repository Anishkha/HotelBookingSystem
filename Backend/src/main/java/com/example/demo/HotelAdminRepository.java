package com.example.demo;

import com.example.demo.HotelAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelAdminRepository extends JpaRepository<HotelAdmin, Long> {
    Optional<HotelAdmin> findByCityAndHotelName(String city, String hotelName);
}
