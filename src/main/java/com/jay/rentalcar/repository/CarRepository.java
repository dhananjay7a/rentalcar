package com.jay.rentalcar.repository;

import com.jay.rentalcar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByAvailability(boolean availability);
}

