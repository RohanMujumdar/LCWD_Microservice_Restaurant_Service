package com.example.restaurantService_microservice.restaurantService.service;

import com.example.restaurantService_microservice.restaurantService.dto.RestaurantDto;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    RestaurantDto saveRestaurant(RestaurantDto dto);

    RestaurantDto updateRestaurant(String id, RestaurantDto dto);

    RestaurantDto getById(String id);

    Optional<RestaurantDto> findByName(String name);

    void deleteRestaurant(String id);

    List<RestaurantDto> getAllRestaurants();
}
