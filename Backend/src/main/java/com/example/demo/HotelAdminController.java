package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hoteladmin") // Base URL
public class HotelAdminController {

    @Autowired
    private HotelAdminRepository hotelAdminRepository; // To interact with the database

    // Add a new hotel
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addHotel(@RequestBody HotelAdmin hotelAdmin) {
        try {
            // Save the hotel
            hotelAdminRepository.save(hotelAdmin);

            // Return success message
            ResponseMessage responseMessage = new ResponseMessage("Hotel added successfully!");
            return ResponseEntity.ok(responseMessage);

        } catch (Exception e) {
            // Log the exception to check what went wrong
            e.printStackTrace();

            // Return error message
            ResponseMessage responseMessage = new ResponseMessage("Error adding hotel: " + e.getMessage());
            return ResponseEntity.status(500).body(responseMessage);
        }
    }
}
