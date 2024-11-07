package com.restaurant.users.infraestructur.driven_rp.persistence;

import com.restaurant.users.infraestructur.driven_rp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersJpaRepository extends JpaRepository<UserEntity, Integer> {
}
