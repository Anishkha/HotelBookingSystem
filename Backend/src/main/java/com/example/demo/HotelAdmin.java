package com.example.demo;

import jakarta.persistence.*;
@Entity
public class HotelAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String hotelName;
    private int roomsAvailable;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public int getRoomsAvailable() {
        return roomsAvailable;
    }
    public void setRoomsAvailable(int roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }
}
