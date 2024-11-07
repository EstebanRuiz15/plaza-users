package com.restaurant.users.domain.interfaces;

import com.restaurant.users.domain.model.User;

public interface IUserPersistencePort {
void  saveUserOwner (User request);
User findByUserID(int id);

}
