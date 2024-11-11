package com.restaurant.users.domain.interfaces;

import com.restaurant.users.domain.model.Employe;
import com.restaurant.users.domain.model.User;

public interface IUserService {
    void  saveUserOwner (User request);
    User getUser(Integer idUser);
    void saveNewClient(User request);
    Integer getUserId();
    void createEmployee(User user);
    Employe getEmployee();
}
