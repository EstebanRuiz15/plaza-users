package com.restaurant.users.infraestructur.driven_rp.mapper;

import com.restaurant.users.domain.model.Employe;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.infraestructur.driven_rp.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface IMapperUserToEntity {
    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(User user);
    User toUser(UserEntity user);
    Employe toEmploye(UserEntity user);
}
