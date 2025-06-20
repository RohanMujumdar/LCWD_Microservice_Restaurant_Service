package com.example.restaurantService_microservice.restaurantService.controller;

import com.example.restaurantService_microservice.restaurantService.dto.RestaurantDto;
import com.example.restaurantService_microservice.restaurantService.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("micro/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto dto) {
        RestaurantDto saved = restaurantService.saveRestaurant(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable String id,
            @RequestBody RestaurantDto dto) {
        RestaurantDto updated = restaurantService.updateRestaurant(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable String id) {
        RestaurantDto restaurant = restaurantService.getById(id);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestaurantDto> getRestaurantByName(@PathVariable String name) {
        return restaurantService.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> list = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
