package com.jay.rentalcar.controller;

import com.jay.rentalcar.model.Rental;
import com.jay.rentalcar.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired  // Inject the service
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // Create a new rental
    @PostMapping
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }

    // Get rental by ID
    @GetMapping("/{rentalId}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long rentalId) {
        return rentalService.getRentalById(rentalId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Get rentals by user ID
    @GetMapping("/user/{userId}")
    public List<Rental> getRentalsByUserId(@PathVariable Long userId) {
        return rentalService.getRentalsByUserId(userId);
    }

    // Delete rental
    @DeleteMapping("/{rentalId}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long rentalId) {
        rentalService.deleteRental(rentalId);
        return ResponseEntity.noContent().build();
    }

    // Update rental
    @PutMapping("/{rentalId}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long rentalId, @RequestBody Rental rentalDetails) {
        try {
            Rental updatedRental = rentalService.updateRental(rentalId, rentalDetails);
            return ResponseEntity.ok(updatedRental);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
