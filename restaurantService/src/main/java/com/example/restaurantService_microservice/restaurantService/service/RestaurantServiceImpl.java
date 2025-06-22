package com.example.restaurantService_microservice.restaurantService.service;

import com.example.restaurantService_microservice.restaurantService.dto.RestaurantDto;
import com.example.restaurantService_microservice.restaurantService.dto.external_foodService.FoodCategoryDto;
import com.example.restaurantService_microservice.restaurantService.dto.external_foodService.FoodItemDto;
import com.example.restaurantService_microservice.restaurantService.entities.Restaurant;
import com.example.restaurantService_microservice.restaurantService.entities.external_foodService.FoodCategory;
import com.example.restaurantService_microservice.restaurantService.entities.external_foodService.FoodItem;
import com.example.restaurantService_microservice.restaurantService.repository.RestaurantRepository;
import com.example.restaurantService_microservice.restaurantService.repository.external_foodService.FoodCategoryRepository;
import com.example.restaurantService_microservice.restaurantService.repository.external_foodService.FoodItemRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private FoodItemRepository foodItemRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

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

    @Override
    @Transactional
    public FoodItemDto addFoodItem(String id, FoodItemDto foodItemDto) {

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        Restaurant restaurant = optionalRestaurant.get();

        FoodItem foodItem = convertDtoToEntity(foodItemDto);
        restaurant.getFoodItemList().add(foodItem);

        restaurantRepository.save(restaurant);

        return foodItemDto;
    }

    @Override
    public FoodCategoryDto addFoodCategory(FoodCategoryDto foodCategoryDto) {

        FoodCategory foodCategory = mapper.map(foodCategoryDto, FoodCategory.class);
        foodCategoryRepository.save(foodCategory);

        return foodCategoryDto;
    }

    @Override
    public List<FoodItemDto> getAllFoodItemsFromRestaurant(String id) {

        List<FoodItem> foodItemList = foodItemRepository.findAllByRestaurantId(id);
        List<FoodItemDto> foodItemDtoList = foodItemList
                                    .stream()
                                    .map(foodItem -> convertEntityToDto(foodItem))
                                    .collect(Collectors.toList());

        return foodItemDtoList;
    }

    @Override
    public List<FoodItemDto> getAllFoodItems() {

        List<FoodItemDto> foodItemDtoList = foodItemRepository.findAll()
                .stream()
                .map(entity -> mapper.map(entity, FoodItemDto.class))
                .collect(Collectors.toList());

        return foodItemDtoList;
    }

    @Override
    public List<FoodCategoryDto> getAllFoodCategories() {
        List<FoodCategoryDto> foodCategoryDtoList = foodCategoryRepository.findAll()
                .stream()
                .map(entity -> mapper.map(entity, FoodCategoryDto.class))
                .collect(Collectors.toList());

        return foodCategoryDtoList;
    }


    private FoodItemDto convertEntityToDto(FoodItem foodItem) {
        FoodItemDto foodItemDto = new FoodItemDto();

        foodItemDto.setId(foodItem.getId());
        foodItemDto.setTitle(foodItem.getTitle());
        foodItemDto.setDescription(foodItem.getDescription());
        foodItemDto.setQuantity(foodItem.getQuantity());
        foodItemDto.setOutOfStock(foodItem.isOutOfStock());
        foodItemDto.setFoodType(foodItem.getFoodType());

        if (foodItem.getFoodCategory() != null) {
            foodItemDto.setFoodCategoryId(foodItem.getFoodCategory().getId());

            FoodCategory foodCategory = foodItem.getFoodCategory();
            FoodCategoryDto foodCategoryDto = new FoodCategoryDto();

            foodCategoryDto.setId(foodCategory.getId());
            foodCategoryDto.setName(foodCategory.getName());
            foodCategoryDto.setDescription(foodCategory.getDescription());

            foodItemDto.setFoodCategoryDto(foodCategoryDto);
        }

        foodItemDto.setRestaurantId(foodItem.getRestaurantId());
        return foodItemDto;
    }

    private FoodItem convertDtoToEntity(FoodItemDto foodItemDto) {
        FoodItem foodItem = new FoodItem();

        foodItem.setId(foodItemDto.getId());
        foodItem.setTitle(foodItemDto.getTitle());
        foodItem.setDescription(foodItemDto.getDescription());
        foodItem.setQuantity(foodItemDto.getQuantity());
        foodItem.setOutOfStock(foodItemDto.isOutOfStock());
        foodItem.setFoodType(foodItemDto.getFoodType());
        foodItem.setRestaurantId(foodItemDto.getRestaurantId());

        if (foodItemDto.getFoodCategoryDto() != null) {
            FoodCategoryDto foodCategoryDto = foodItemDto.getFoodCategoryDto();

            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setId(foodCategoryDto.getId());
            foodCategory.setName(foodCategoryDto.getName());
            foodCategory.setDescription(foodCategoryDto.getDescription());

            foodItem.setFoodCategory(foodCategory);
        } else if (foodItemDto.getFoodCategoryId() != null) {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setId(foodItemDto.getFoodCategoryId());
            foodItem.setFoodCategory(foodCategory);
        }

        return foodItem;
    }
}

