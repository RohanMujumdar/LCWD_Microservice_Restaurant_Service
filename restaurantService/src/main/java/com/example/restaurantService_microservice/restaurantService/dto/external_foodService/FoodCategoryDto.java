// FoodCategoryDto.java
package com.example.restaurantService_microservice.restaurantService.dto.external_foodService;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCategoryDto {
    private String id;
    private String name;
    private String description;
    private List<FoodItemDto> foodItems;
}
