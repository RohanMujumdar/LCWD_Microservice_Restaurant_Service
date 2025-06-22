// FoodItemDto.java
package com.example.restaurantService_microservice.restaurantService.dto.external_foodService;


import com.example.restaurantService_microservice.restaurantService.entities.external_foodService.FoodType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
    private String id;
    private String title;
    private String description;
    private int quantity;
    private boolean outOfStock;
    private FoodType foodType;
    private String restaurantId;
    private String foodCategoryId;
    private FoodCategoryDto foodCategoryDto;
}
