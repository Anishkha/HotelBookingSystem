package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private CustomerReservationRepository customerReservationRepository;

    @Autowired
    private HotelAdminRepository hotelAdminRepository;

    /**
     * Reserve a room if Aadhar is valid and rooms are available.
     */
    public String reserveRoom(CustomerReservation reservation) {
        logger.info("Starting room reservation process for: {}", reservation);

        if (!isValidAadhar(reservation.getAadharNumber())) {
            logger.warn("Invalid Aadhar number provided: {}", reservation.getAadharNumber());
            return "Invalid Aadhar number.";
        }

        // Find hotel by city and name
        HotelAdmin hotelAdmin = hotelAdminRepository
                .findByCityAndHotelName(reservation.getCity(), reservation.getHotelName())
                .orElse(null);

        if (hotelAdmin == null) {
            logger.warn("Hotel not found: city={}, hotelName={}", reservation.getCity(), reservation.getHotelName());
            return "Hotel not found in the specified city.";
        }

        logger.info("Hotel found: {}, Available rooms: {}", hotelAdmin.getHotelName(), hotelAdmin.getRoomsAvailable());

        if (hotelAdmin.getRoomsAvailable() > 0) {
            reservation.setReservationStatus(true);
            reservation.setBookingStatus(false);
            reservation.setReservationTime(LocalDateTime.now());
            customerReservationRepository.save(reservation);
            logger.info("Reservation saved successfully with ID: {}", reservation.getId());

            // Update rooms available
            hotelAdmin.setRoomsAvailable(hotelAdmin.getRoomsAvailable() - 1);
            hotelAdminRepository.save(hotelAdmin);
            logger.info("Room count updated. Rooms remaining: {}", hotelAdmin.getRoomsAvailable());

            return "Room reserved successfully!";
        } else {
            logger.warn("No rooms available for hotel: {}", reservation.getHotelName());
            return "No rooms available at " + reservation.getHotelName() + " in " + reservation.getCity() +
                   ". Please try a different hotel or check back later.";
        }
    }

    /**
     * Confirm a booking for a given reservation ID.
     */
    public String confirmBooking(Long reservationId) {
        logger.info("Confirming booking for reservation ID: {}", reservationId);

        CustomerReservation reservation = customerReservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    logger.error("Reservation not found with ID: {}", reservationId);
                    return new RuntimeException("Reservation not found");
                });

        reservation.setBookingStatus(true);
        reservation.setBookingTime(LocalDateTime.now());
        customerReservationRepository.save(reservation);

        logger.info("Booking confirmed successfully for reservation ID: {}", reservationId);
        return "Booking confirmed successfully!";
    }

    /**
     * Cancel a booking and update room availability.
     */
    public void cancelBooking(Long reservationId) {
        logger.info("Canceling reservation with ID: {}", reservationId);

        CustomerReservation reservation = customerReservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    logger.error("Reservation not found with ID: {}", reservationId);
                    return new RuntimeException("Reservation not found");
                });

        // Update room availability
        HotelAdmin hotelAdmin = hotelAdminRepository
                .findByCityAndHotelName(reservation.getCity(), reservation.getHotelName())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotelAdmin.setRoomsAvailable(hotelAdmin.getRoomsAvailable() + 1);
        hotelAdminRepository.save(hotelAdmin);

        logger.info("Rooms updated for hotel: {}, Available rooms: {}", hotelAdmin.getHotelName(), hotelAdmin.getRoomsAvailable());

        // Delete the reservation
        customerReservationRepository.delete(reservation);
        logger.info("Reservation deleted successfully with ID: {}", reservationId);
    }

    /**
     * Automatically cancel unconfirmed reservations older than 24 hours.
     */
    @Scheduled(fixedRate = 60000) // Run every minute
    public void autoCancelUnconfirmedReservations() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        logger.info("Running auto-cancellation for unconfirmed reservations before: {}", cutoffTime);

        List<CustomerReservation> expiredReservations = customerReservationRepository
                .findByReservationStatusTrueAndBookingStatusFalseAndReservationTimeBefore(cutoffTime);

        logger.info("Found {} unconfirmed reservations to cancel.", expiredReservations.size());

        for (CustomerReservation reservation : expiredReservations) {
            cancelReservationDueToExpiry(reservation);
        }
    }

    /**
     * Helper method to cancel reservations due to expiry.
     */
    private void cancelReservationDueToExpiry(CustomerReservation reservation) {
        logger.info("Auto-canceling reservation ID: {}", reservation.getId());

        HotelAdmin hotelAdmin = hotelAdminRepository
                .findByCityAndHotelName(reservation.getCity(), reservation.getHotelName())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotelAdmin.setRoomsAvailable(hotelAdmin.getRoomsAvailable() + 1);
        hotelAdminRepository.save(hotelAdmin);

        customerReservationRepository.delete(reservation);
        logger.info("Reservation ID {} auto-canceled and room availability updated.", reservation.getId());
    }

    /**
     * Validate Aadhar number format (12-digit number).
     */
    private boolean isValidAadhar(String aadharNumber) {
        return aadharNumber != null && aadharNumber.matches("\\d{12}");
    }
}
