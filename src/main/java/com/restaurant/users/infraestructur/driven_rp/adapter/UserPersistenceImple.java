package com.restaurant.users.infraestructur.driven_rp.adapter;

import com.restaurant.users.domain.interfaces.IEncoderPort;
import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.infraestructur.driven_rp.entity.UserEntity;
import com.restaurant.users.infraestructur.driven_rp.mapper.IMapperUserToEntity;
import com.restaurant.users.infraestructur.driven_rp.persistence.UsersJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserPersistenceImple implements IUserPersistencePort {
    private final IMapperUserToEntity mapperToEntity;
    private final IEncoderPort encoder;
    private final UsersJpaRepository repositoryJpa;


    @Override
    public void saveUserOwner(User request) {
        UserEntity user=mapperToEntity.toUserEntity(request);
        String passEncoder= encoder.encode(user.getPassword());
        user.setPassword(passEncoder);
        repositoryJpa.save(user);
    }

    @Override
    public User findByUserID(int id) {
        Optional<UserEntity> optionalUser = repositoryJpa.findById(id);

        return optionalUser.map(mapperToEntity::toUser)
                .orElse(null);
    }
}
