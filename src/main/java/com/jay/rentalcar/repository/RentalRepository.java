package com.jay.rentalcar.repository;


import com.jay.rentalcar.model.Car;
import com.jay.rentalcar.model.Rental;
import com.jay.rentalcar.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    // Custom method to find rentals by user ID
    List<Rental> findByUserId(Long userId);
    List<Rental> findByUserAndCarAndReturnDateIsNull(User user, Car car);
}