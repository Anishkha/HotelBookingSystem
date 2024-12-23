package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CustomerReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @Column(unique = true, length = 12)
    private String aadharNumber;
    private String city;
    private String hotelName;
    private Boolean reservationStatus = false;
    private Boolean bookingStatus = false;
    private LocalDateTime reservationTime;
    private LocalDateTime bookingTime;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAadharNumber() {
        return aadharNumber;
    }
    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getHotelName() {
        return hotelName;
    }
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    public Boolean getReservationStatus() {
        return reservationStatus;
    }
    public void setReservationStatus(Boolean reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    public Boolean getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(Boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}
