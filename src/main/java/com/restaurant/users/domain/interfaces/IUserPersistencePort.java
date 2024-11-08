package com.restaurant.users.domain.interfaces;

import com.restaurant.users.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {
void  saveUser (User request);
User findByUserID(int id);
Optional<User> findByEmail(String email);

}
