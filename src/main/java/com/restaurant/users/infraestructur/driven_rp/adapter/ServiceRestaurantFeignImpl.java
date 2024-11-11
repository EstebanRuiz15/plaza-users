package com.restaurant.users.infraestructur.driven_rp.adapter;

import com.restaurant.users.domain.exception.ErrorFeignException;
import com.restaurant.users.domain.interfaces.IServiceRestaurantFeig;
import com.restaurant.users.domain.utils.ConstantsDomain;
import com.restaurant.users.infraestructur.feign.RestaurantClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceRestaurantFeignImpl implements IServiceRestaurantFeig {
    private final RestaurantClient feignClient;
    @Override
    public Integer getRestaurantIdtoIdOwner(Integer idOwner) {
        try {
            return feignClient.getRestaurantOwnerId(idOwner);
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE)+e);
        }
    }
}
