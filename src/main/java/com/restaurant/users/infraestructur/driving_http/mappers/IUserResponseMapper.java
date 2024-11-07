package com.restaurant.users.infraestructur.driving_http.mappers;

import com.restaurant.users.domain.model.User;
import com.restaurant.users.infraestructur.driving_http.dtos.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserResponseMapper {
    @Mapping(target = "rol", ignore = true)
    UserResponseDto toUserResponse(User user);
}
