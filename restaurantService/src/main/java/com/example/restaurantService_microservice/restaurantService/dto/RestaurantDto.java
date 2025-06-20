package com.example.restaurantService_microservice.restaurantService.dto;

import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RestaurantDto {

    private String id;
    private String name;
    private String address;
    private String phone;

    private List<String> pictures = new ArrayList<>();

    private boolean open = false;
    private LocalTime openTime;
    private LocalTime closeTime;

    private String aboutRestaurant;
}
