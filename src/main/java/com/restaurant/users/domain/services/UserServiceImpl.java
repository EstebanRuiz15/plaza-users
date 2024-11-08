package com.restaurant.users.domain.services;

import com.restaurant.users.domain.exception.ErrorExceptionParam;
import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.interfaces.IUserService;
import com.restaurant.users.domain.model.RoleEnum;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.domain.utils.ConstantsDomain;

import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements IUserService {

private final IUserPersistencePort persistance;

    public UserServiceImpl(IUserPersistencePort persistance) {
        this.persistance = persistance;
    }

    @Override
    public void saveUserOwner(User request) {
    isValidParams(request);
    request.setRol(RoleEnum.OWNER);
    persistance.saveUser(request);
    }

    @Override
    public User getUser(Integer idUser) {
        return persistance.findByUserID(idUser);
    }

    @Override
    public void saveNewClient(User request) {
        isValidParams(request);
        request.setRol(RoleEnum.CLIENT);
        persistance.saveUser(request);
    }

    private void isValidParams(User user){
        Optional<User> userr= persistance.findByEmail(user.getEmail());
        if(userr.isPresent()) throw new ErrorExceptionParam("the email is al ready exist");

        String regex = ConstantsDomain.REGEX_PASSWORD;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getPassword());
        if(!matcher.matches()) {
            throw new ErrorExceptionParam(ConstantsDomain.PASSWORD_INVALID);
        }

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(user.getBirthDay());

        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        if (age < 10 || age > 100) {
            throw new ErrorExceptionParam(ConstantsDomain.ERROR_MESSAGE_BIRTHDATE);

        }
    }
}
