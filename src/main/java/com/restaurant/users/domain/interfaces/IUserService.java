package com.restaurant.users.domain.interfaces;

import com.restaurant.users.domain.model.User;

public interface IUserService {
    void  saveUserOwner (User request);
    User getUser(Integer idUser);
}
