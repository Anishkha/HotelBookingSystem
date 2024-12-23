package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Enables the scheduling feature
public class HotelBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingSystemApplication.class, args);
	}
}
