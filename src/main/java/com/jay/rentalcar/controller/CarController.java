package com.jay.rentalcar.controller;

import com.jay.rentalcar.model.Car;
import com.jay.rentalcar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    // View all available cars
    @GetMapping
    public ResponseEntity<List<Car>> getAvailableCars() {
        List<Car> availableCars = carService.getAvailableCars();
        return ResponseEntity.ok(availableCars);
    }

    // Rent a car (user)
    @PostMapping("/rent/{userId}/{carId}")
    public ResponseEntity<String> rentCar(@PathVariable Long userId, @PathVariable Long carId, @RequestParam int rentalDays) {
        if (rentalDays <= 0) {
            return ResponseEntity.badRequest().body("Rental days must be greater than zero.");
        }
        String result = carService.rentCar(userId, carId, rentalDays);
        return ResponseEntity.ok(result);
    }

    // Return a car (user)
    @PutMapping("/return/{user-id}/{car-id}")
    public ResponseEntity<String> returnCar(@PathVariable("user-id") Long userId , @PathVariable("car-id") Long carId) {
        String result = carService.returnCar(userId, carId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{carId}")
    public ResponseEntity<byte[]> getCarImageById(@PathVariable Long carId) {
        ResponseEntity<byte[]> responseEntity = carService.getImage(carId);

        byte[] car = responseEntity.getBody();
        if(car!=null){
            return ResponseEntity.ok(car);}

        return ResponseEntity.notFound().build();
    }
}