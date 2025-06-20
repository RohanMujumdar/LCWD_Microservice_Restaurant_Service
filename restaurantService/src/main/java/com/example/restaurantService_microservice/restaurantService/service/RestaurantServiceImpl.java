package com.example.restaurantService_microservice.restaurantService.service;

import com.example.restaurantService_microservice.restaurantService.dto.RestaurantDto;
import com.example.restaurantService_microservice.restaurantService.entities.Restaurant;
import com.example.restaurantService_microservice.restaurantService.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public RestaurantDto saveRestaurant(RestaurantDto dto) {
        dto.setId(UUID.randomUUID().toString());
        Restaurant entity = mapper.map(dto, Restaurant.class);
        Restaurant saved = restaurantRepository.save(entity);
        return mapper.map(saved, RestaurantDto.class);
    }

    @Override
    public RestaurantDto updateRestaurant(String id, RestaurantDto dto) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }

        Restaurant restaurant = optional.get();
        mapper.map(dto, restaurant); // update fields from dto to entity
        Restaurant updated = restaurantRepository.save(restaurant);
        return mapper.map(updated, RestaurantDto.class);
    }

    @Override
    public RestaurantDto getById(String id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
        return mapper.map(restaurant, RestaurantDto.class);
    }

    @Override
    @Transactional
    public Optional<RestaurantDto> findByName(String name) {

//      map(...) is a method available on Optional<T>, which transforms the value inside if it's present.
//      If the Optional<Restaurant> is empty, the result remains an empty Optional<RestaurantDto>.
        return restaurantRepository.findByName(name)
                .map(restaurant -> mapper.map(restaurant, RestaurantDto.class));
    }

    @Override
    public void deleteRestaurant(String id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(entity -> mapper.map(entity, RestaurantDto.class))
                .collect(Collectors.toList());
    }
}
