package com.restaurant.users.infraestructur.driven_rp.adapter;

import com.restaurant.users.domain.interfaces.IEncoderPort;
import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.domain.utils.ConstantsDomain;
import com.restaurant.users.infraestructur.driven_rp.entity.UserEntity;
import com.restaurant.users.infraestructur.driven_rp.mapper.IMapperUserToEntity;
import com.restaurant.users.infraestructur.driven_rp.persistence.UsersJpaRepository;
import com.restaurant.users.infraestructur.security_config.jwt_configuration.JwtService;
import com.restaurant.users.infraestructur.util.InfraConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserPersistenceImple implements IUserPersistencePort {
    private final IMapperUserToEntity mapperToEntity;
    private final IEncoderPort encoder;
    private final UsersJpaRepository repositoryJpa;
    private final JwtService jwtService;


    @Override
    public void saveUser(User request) {
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

    @Override
    public Optional<User> findByEmail(String email) {
        return repositoryJpa.findByEmail(email)
                .map(mapperToEntity::toUser);
    }

    @Override
    public Integer getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String jwt = request.getHeader(InfraConstants.AUTHORIZATION);
        jwt = jwt.substring(InfraConstants.SEVEN);
        return jwtService.extractUserId(jwt);
    }
}
