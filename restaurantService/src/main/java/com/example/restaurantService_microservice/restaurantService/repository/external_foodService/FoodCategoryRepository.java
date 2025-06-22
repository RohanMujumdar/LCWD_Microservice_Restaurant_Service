package com.example.restaurantService_microservice.restaurantService.repository.external_foodService;


import com.example.restaurantService_microservice.restaurantService.entities.external_foodService.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, String> {

}
