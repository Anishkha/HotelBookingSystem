package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Allow requests from your frontend's port (update with your actual frontend URL)
public class ReservationController {
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    @Autowired
    private ReservationService reservationService;

    // Endpoint to reserve a room
    @PostMapping("/reserve")
    public ResponseEntity<Object> reserveRoom(@RequestBody CustomerReservation reservation) {
        String result = reservationService.reserveRoom(reservation);
        
        // Return response as JSON
        return ResponseEntity.ok().body(new ResponseMessage(result));  // Wrap the result message in ResponseMessage
    }

    // Endpoint to confirm a booking
    @PutMapping("/confirm/{reservationId}")
    public ResponseEntity<Object> confirmBooking(@PathVariable("reservationId") Long reservationId) {
        logger.info("Received confirmation request for Reservation ID: " + reservationId);
        try {
            String result = reservationService.confirmBooking(reservationId);
            return ResponseEntity.ok(new ResponseMessage(result)); // Send message as JSON response
        } catch (RuntimeException e) {
            logger.error("Error confirming reservation ID: " + reservationId, e);
            return ResponseEntity.status(404).body(new ResponseMessage("Reservation not found: " + reservationId)); // Error message as JSON
        }
    }

    // Endpoint to cancel and delete a reservation
    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<Object> cancelBooking(@PathVariable("reservationId") Long reservationId) {
        logger.info("Received cancellation request for Reservation ID: " + reservationId);
        try {
            reservationService.cancelBooking(reservationId);
            return ResponseEntity.ok(new ResponseMessage("Reservation canceled and deleted successfully!")); // Success message as JSON
        } catch (RuntimeException e) {
            logger.error("Error canceling reservation ID: " + reservationId, e);
            return ResponseEntity.status(404).body(new ResponseMessage("Reservation not found: " + reservationId)); // Error message as JSON
        }
    }
}
