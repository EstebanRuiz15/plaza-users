package com.restaurant.users.infraestructur.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
    @FeignClient(name = "plazoletaaplication", url = "${user.client.url}", configuration = FeignConfig.class)
    public interface RestaurantClient {
        @GetMapping("getRestId")
        Integer getRestaurantOwnerId(@RequestParam Integer idOwner);

    }

