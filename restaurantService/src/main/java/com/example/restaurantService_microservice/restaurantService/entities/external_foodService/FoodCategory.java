package com.example.restaurantService_microservice.restaurantService.entities.external_foodService;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurant_service_food_category")
public class FoodCategory {

    @Id
    private String id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodItem> foodItemList = new ArrayList<>();

}
