package com.example.restaurantService_microservice.restaurantService.repository.external_foodService;


import com.example.restaurantService_microservice.restaurantService.entities.external_foodService.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FoodItemRepository extends JpaRepository<FoodItem, String> {

    List<FoodItem> findAllByRestaurantId(String id);
}
