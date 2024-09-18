package com.jay.rentalcar.service;
import com.jay.rentalcar.repository.UserRepository;
import com.jay.rentalcar.repository.RentalRepository;
import com.jay.rentalcar.model.Car;
import com.jay.rentalcar.model.Rental;
import com.jay.rentalcar.model.User;
import com.jay.rentalcar.repository.CarRepository;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public List<Car> getAvailableCars() {
        return carRepository.findByAvailability(true);
    }

    public Car addCar(Car car , MultipartFile imageFile) throws IOException {
        car.setImageData(imageFile.getBytes());
        car.setImageType(imageFile.getContentType());
        car.setImageName(imageFile.getOriginalFilename());
        car.setAvailability(true); // Cars are available by default
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car updatedCar, MultipartFile imageFile) throws IOException {
        Optional<Car> existingCar = carRepository.findById(id);
        if (existingCar.isPresent()) {
            Car car = existingCar.get();
            car.setModel(updatedCar.getModel());
            car.setMake(updatedCar.getMake());
            car.setYear(updatedCar.getYear());
            car.setPricePerDay(updatedCar.getPricePerDay());
            car.setImageData(imageFile.getBytes());
            car.setImageType(imageFile.getContentType());
            car.setImageName(imageFile.getOriginalFilename());
            return carRepository.save(car);
        }
        throw new RuntimeException("Car not found");
    }
    public ResponseEntity<byte[]> getImage(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            Car c = car.get();
    
            // Assuming imageType is a string like "image/jpeg" or "image/png"
            MediaType mediaType = MediaType.parseMediaType(c.getImageType());
    
            // Return image data with correct content type
            return ResponseEntity.ok()
                                 .contentType(mediaType)
                                 .body(c.getImageData());
        }
        return ResponseEntity.notFound().build();
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

     public String rentCar(Long userId, Long carId, int rentalDays) {
        // Find the user and the car by their IDs
        Optional<User> user = userRepository.findById(userId);
        Optional<Car> car = carRepository.findById(carId);

        if (user.isEmpty() || car.isEmpty()) {
            return "User or Car not found.";
        }

        Car selectedCar = car.get();

        // Check if the car is available
        if (!selectedCar.isAvailability()) {
            return "Car is not available.";
        }

        // Calculate total rental price (pricePerDay * rentalDays)
        double totalPrice = selectedCar.getPricePerDay() * rentalDays;

        // Simulate payment confirmation (you can add real payment logic here)
        boolean paymentConfirmed = confirmPayment(user.get(), totalPrice);
        if (!paymentConfirmed) {
            return "Payment failed. Car rental cancelled.";
        }

        // If payment is confirmed, rent the car
        selectedCar.setAvailability(false); // Set car to unavailable

        // Save the rental record

        Rental rental = new Rental();
        rental.setUser(user.get());
        rental.setCar(selectedCar);
        rental.setRentalDate(LocalDateTime.now());
        rental.setRentalDays(rentalDays); // Set rental days
        rental.setTotalPrice(totalPrice); // Set total price

        rentalRepository.save(rental); // Save the rental record
        carRepository.save(selectedCar); // Update the car's availability

        return "Car rented successfully. Total price: $" + totalPrice;
    }


    public String returnCar(Long userId,Long carId) {
            // Find the user and car by their IDs
            Optional<User> user = userRepository.findById(userId);
            Optional<Car> car = carRepository.findById(carId);
        
            if (user.isEmpty() || car.isEmpty()) {
                return "User or Car not found.";
            }
        
            Car selectedCar = car.get();
        
            // Check if the car is rented by the user
            List<Rental> rentals = rentalRepository.findByUserAndCarAndReturnDateIsNull(user.get(), selectedCar);
        
            if (rentals.isEmpty()) {
                return "No active rentals found for this car by this user.";
            }
        
            // Mark the car as returned
            Rental activeRental = rentals.get(0); // Assuming only one active rental per car
            activeRental.setReturnDate(LocalDateTime.now());
            rentalRepository.save(activeRental);
        
            // Set the car to available
            selectedCar.setAvailability(true);
            carRepository.save(selectedCar);
        
            return "Car returned successfully.";
    }


    // Fetch rentals by car ID in the CarService
    public List<Rental> getCarRentals(Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        if (car.isPresent()) {
            return car.get().getRentals();
        }
        throw new RuntimeException("Car not found");
    }

    private boolean confirmPayment(User user, double totalPrice) {
        // Simulate some payment processing logic (always returns true for simplicity)
        return true; // Assume payment is successful
    }

}

