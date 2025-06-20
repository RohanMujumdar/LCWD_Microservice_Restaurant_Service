package com.example.restaurantService_microservice.restaurantService.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    private String id;
    private String name;
    private String address;
    private String phone;

    // Using this below annotation for storing a collection of elements.
    @ElementCollection
    private List<String> pictures = new ArrayList<>();

    private boolean open = false;
    private LocalTime openTime;
    private LocalTime closeTime;

    @Lob
    private String aboutRestaurant;
}
