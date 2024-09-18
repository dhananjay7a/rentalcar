package com.jay.rentalcar.service;

import com.jay.rentalcar.model.Rental;
import com.jay.rentalcar.model.User;
import com.jay.rentalcar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Find a user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Save a new user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Get all rentals of a user by user ID
    public List<Rental> getUserRentals(Long userId) {
        // Find the user by ID
        Optional<User> user = userRepository.findById(userId);

        // If user is found, return their rental history
        if (user.isPresent()) {
            return user.get().getRentals(); // Fetches rentals associated with the user
        }

        // Throw an exception if the user is not found
        throw new RuntimeException("User not found");
    }
}
