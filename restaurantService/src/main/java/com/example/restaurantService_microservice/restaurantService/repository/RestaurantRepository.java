package com.example.restaurantService_microservice.restaurantService.repository;

import com.example.restaurantService_microservice.restaurantService.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    Optional<Restaurant> findByName(String name);
}
