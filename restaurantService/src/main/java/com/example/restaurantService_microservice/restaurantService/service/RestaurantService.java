package com.example.restaurantService_microservice.restaurantService.service;

import com.example.restaurantService_microservice.restaurantService.dto.RestaurantDto;
import com.example.restaurantService_microservice.restaurantService.dto.external_foodService.FoodCategoryDto;
import com.example.restaurantService_microservice.restaurantService.dto.external_foodService.FoodItemDto;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    RestaurantDto saveRestaurant(RestaurantDto dto);

    RestaurantDto updateRestaurant(String id, RestaurantDto dto);

    RestaurantDto getById(String id);

    Optional<RestaurantDto> findByName(String name);

    void deleteRestaurant(String id);

    List<RestaurantDto> getAllRestaurants();

    FoodItemDto addFoodItem(String restaurantName, FoodItemDto foodItemDto);

    FoodCategoryDto addFoodCategory(FoodCategoryDto foodCategoryDto);

    List<FoodItemDto> getAllFoodItemsFromRestaurant(String restaurantName);

    List<FoodItemDto> getAllFoodItems();

    List<FoodCategoryDto> getAllFoodCategories();
}
