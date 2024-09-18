package com.jay.rentalcar.service;

// File: src/main/java/com/example/rental/service/RentalService.java


import com.jay.rentalcar.model.Rental;
import com.jay.rentalcar.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    @Autowired  // Inject the repository
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // Create a new rental
    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    // Find a rental by ID
    public Optional<Rental> getRentalById(Long rentalId) {
        return rentalRepository.findById(rentalId);
    }

    // Find rentals by user ID
    public List<Rental> getRentalsByUserId(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    // Delete a rental by ID
    public void deleteRental(Long rentalId) {
        rentalRepository.deleteById(rentalId);
    }

    // Update rental
    public Rental updateRental(Long rentalId, Rental rentalDetails) {
        return rentalRepository.findById(rentalId)
            .map(rental -> {
                rental.setCar(rentalDetails.getCar());
                rental.setRentalDate(rentalDetails.getRentalDate());
                return rentalRepository.save(rental);
            })
            .orElseThrow(() -> new RuntimeException("Rental not found"));
    }
}

