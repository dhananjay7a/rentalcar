package com.jay.rentalcar.controller;

import com.jay.rentalcar.model.Rental;
import com.jay.rentalcar.model.User;
import com.jay.rentalcar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken.");
        }
        user.setPassword(user.getPassword());
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully."+savedUser.getId());
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent() && user.getPassword().equals(existingUser.get().getPassword())) {
            return ResponseEntity.ok("Login successful - User");
        }
        return ResponseEntity.badRequest().body("Invalid username or password.");
    }

    @PostMapping("/{userId}/rentals")
    public ResponseEntity<List<Rental>> getUserRentals(@PathVariable Long userId) {
        List<Rental> rentals = userService.getUserRentals(userId);
        return ResponseEntity.ok(rentals);
    }
}
