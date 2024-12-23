package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerReservationRepository extends JpaRepository<CustomerReservation, Long> {
    // Find reservation by Aadhar number
    Optional<CustomerReservation> findByAadharNumber(String aadharNumber);
    // Find unconfirmed reservations older than 24 hours
    List<CustomerReservation> findByReservationStatusTrueAndBookingStatusFalseAndReservationTimeBefore(LocalDateTime cutoffTime);
}
