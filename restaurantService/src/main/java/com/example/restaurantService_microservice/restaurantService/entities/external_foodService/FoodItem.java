package com.example.restaurantService_microservice.restaurantService.entities.external_foodService;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_service_food_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodItem {

    @Id
    private String id;

    private String title;

    private String description;

    private int quantity;

    private boolean outOfStock = true;

    @Enumerated(EnumType.STRING)
    private FoodType foodType=FoodType.VEG;

    @ManyToOne
    private FoodCategory foodCategory;

    // Store the restaurant information inside food item
    // With the help of restaurantId, we will get to know that a particular food item,
    // is of which restaurant.

    @Column(nullable = false)
    private String restaurantId;
}

