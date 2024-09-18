package com.jay.rentalcar.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  //one rental is associated with one user, but a user can have multiple rentals.
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-rentals")
    /**
     * This annotation is used to avoid infinite recursion in the JSON representation
     * of the model. It is used to only include the user id in the JSON representation
     * of the rental.
     */
    private User user;

    @ManyToOne  // one Rental is associated with one Car, but a Car can be associated with multiple Rentals.
    @JoinColumn(name = "car_id", nullable = false)
    @JsonBackReference(value = "car-rentals")
    private Car car;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy  HH:mm:ss")
    private LocalDateTime rentalDate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy  HH:mm:ss")
    private LocalDateTime returnDate;

    private int rentalDays; // Number of days for the rental
    private double totalPrice; // Total price for the rental

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
