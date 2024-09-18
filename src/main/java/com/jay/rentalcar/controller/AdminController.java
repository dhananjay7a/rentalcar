package com.jay.rentalcar.controller;

import com.jay.rentalcar.model.Admin;
import com.jay.rentalcar.model.Car;
import com.jay.rentalcar.service.AdminService;
import com.jay.rentalcar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CarService carService;

  
    // Admin login
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody Admin admin) {
        Optional<Admin> existingAdmin = adminService.findByUsername(admin.getUsername());
        if (existingAdmin.isPresent() && admin.getPassword().equals(existingAdmin.get().getPassword())) {
            return ResponseEntity.ok("Login successful - Admin");
        }
        return ResponseEntity.badRequest().body("Invalid admin credentials.");
    }

    // Add a new car (admin only)
    @PostMapping(value = "/cars", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addCar(@RequestPart Car car , @RequestPart MultipartFile imageFile ) {
        try {
            carService.addCar(car, imageFile);
            return ResponseEntity.ok("Car added successfully.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        }
        
    }

    // Update a car (admin only)
    @PutMapping("/cars/update/{id}")
    public ResponseEntity<String> updateCar(@PathVariable Long id, @RequestPart Car updatedCar , @RequestPart MultipartFile imageFile ) {
        try {
            carService.updateCar(id, updatedCar, imageFile);
            return ResponseEntity.ok("Car updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        }
    }

    // Delete a car (admin only)
    @DeleteMapping("/cars/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car deleted successfully.");
    }
}

