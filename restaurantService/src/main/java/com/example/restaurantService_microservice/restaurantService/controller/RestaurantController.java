package com.example.restaurantService_microservice.restaurantService.controller;

import com.example.restaurantService_microservice.restaurantService.dto.RestaurantDto;
import com.example.restaurantService_microservice.restaurantService.dto.external_foodService.FoodCategoryDto;
import com.example.restaurantService_microservice.restaurantService.dto.external_foodService.FoodItemDto;
import com.example.restaurantService_microservice.restaurantService.service.RestaurantService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("micro/api/restaurants")
@Slf4j
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto dto) {
        RestaurantDto saved = restaurantService.saveRestaurant(dto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{id}/food-items")
    public ResponseEntity<FoodItemDto> addFoodItem(
            @PathVariable String id,
            @RequestBody FoodItemDto foodItemDto) {

        FoodItemDto addedFoodItem = restaurantService.addFoodItem(id, foodItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedFoodItem);
    }

    @PostMapping("/food-category")
    public ResponseEntity<FoodCategoryDto> addFoodCategory(@RequestBody FoodCategoryDto foodCategoryDto)
    {
        FoodCategoryDto ans = restaurantService.addFoodCategory(foodCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ans);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable String id,
            @RequestBody RestaurantDto dto) {
        RestaurantDto updated = restaurantService.updateRestaurant(id, dto);
        return ResponseEntity.ok(updated);
    }

    int counter = 0;

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable String id) {
        RestaurantDto restaurant = restaurantService.getById(id);
        System.out.println("Retried");

        counter++;
        if(counter<=3)
        {
            System.out.println("Retry Number: "+ counter);

            throw new RuntimeException("failed");
        }

        log.info("Restaurant bodY: {}", restaurant);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/food-items/{id}")
    public ResponseEntity<List<FoodItemDto>> getAllFoodItemsFromRestaurant(@PathVariable String id)
    {
        List<FoodItemDto> foodItemDtoList = restaurantService.getAllFoodItemsFromRestaurant(id);
        return ResponseEntity.ok(foodItemDtoList);
    }

    @RateLimiter(name = "get-all-restaurants-rate-limit", fallbackMethod = "fallBackRateLimiter")
    @GetMapping("/food-items/")
    public ResponseEntity<List<FoodItemDto>> getAllFoodItems()
    {
        List<FoodItemDto> allFoodItems = restaurantService.getAllFoodItems();
        return ResponseEntity.ok(allFoodItems);
    }

//    The RequestNotPermitted ex is required for fallBackMethod to work.
    public ResponseEntity<String> fallBackRateLimiter(RequestNotPermitted ex)
    {
        return ResponseEntity.
                status(HttpStatus.TOO_MANY_REQUESTS).
                body("Too many Requests, please try later");
    }

    @GetMapping("/food-categories/")
    public ResponseEntity<List<FoodCategoryDto>> getAllFoodCategories()
    {
        List<FoodCategoryDto> allFoodCategories = restaurantService.getAllFoodCategories();
        return ResponseEntity.ok(allFoodCategories);
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
