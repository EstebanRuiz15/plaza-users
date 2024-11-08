package com.restaurant.users.infraestructur.driving_http.mappers;

import com.restaurant.users.domain.model.User;
import com.restaurant.users.infraestructur.driving_http.dtos.request.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {

    @Mapping(target = "rol", ignore = true)
    User userRequestDtoToUser(UserRequestDto userRequestDto);

    UserRequestDto userToUserRequestDto(User user);
}
