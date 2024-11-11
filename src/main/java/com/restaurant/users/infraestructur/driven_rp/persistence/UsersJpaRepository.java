package com.restaurant.users.infraestructur.driven_rp.persistence;

import com.restaurant.users.domain.model.RoleEnum;
import com.restaurant.users.infraestructur.driven_rp.entity.UserEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.rol = :rol AND u.rest_id = :restId")
    Optional<UserEntity> findByRoleAndRestId(@Param("rol") RoleEnum rol, @Param("restId") Integer restId);
}
