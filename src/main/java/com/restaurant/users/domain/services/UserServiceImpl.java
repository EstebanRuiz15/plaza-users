package com.restaurant.users.domain.services;

import com.restaurant.users.domain.exception.ErrorExceptionParam;
import com.restaurant.users.domain.exception.ExceptionRestaurantNotFound;
import com.restaurant.users.domain.interfaces.IServiceRestaurantFeig;
import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.interfaces.IUserService;
import com.restaurant.users.domain.model.Employe;
import com.restaurant.users.domain.model.RoleEnum;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.domain.utils.ConstantsDomain;

import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements IUserService {

private final IUserPersistencePort persistance;
private final IServiceRestaurantFeig feignClient;
    public UserServiceImpl(IUserPersistencePort persistance, IServiceRestaurantFeig feignClient) {
        this.persistance = persistance;
        this.feignClient = feignClient;
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

    @Override
    public Integer getUserId() {
        return persistance.getUserId();
    }

    @Override
    public void createEmployee(User user, String rol) {
        isValidParams(user);
        if(!rol.equalsIgnoreCase(RoleEnum.EMPLOYEE.toString()) && !rol.equalsIgnoreCase(RoleEnum.CHEF.toString()) ){
            throw new ErrorExceptionParam(ConstantsDomain.ROL_INVALID);
        }
        if(rol.equalsIgnoreCase(RoleEnum.CHEF.toString())) user.setRol(RoleEnum.CHEF);
        if(rol.equalsIgnoreCase(RoleEnum.EMPLOYEE.toString()))user.setRol(RoleEnum.EMPLOYEE);
        Integer OwnerId=persistance.getUserId();
        Integer restId=feignClient.getRestaurantIdtoIdOwner(OwnerId);
        if(restId == null)throw new ExceptionRestaurantNotFound(ConstantsDomain.REST_NOT_FOUND+OwnerId);
        persistance.saveUserEmployee(user,restId);
    }

    @Override
    public Employe getEmployee() {
        Integer emloye=persistance.getUserId();
        return persistance.findByEmployeID(emloye);
    }

    @Override
    public Employe getChefRestaurantId(Integer restId) {
        return persistance.getChefRestaurantId(restId);
    }

    private void isValidParams(User user){
        Optional<User> userr= persistance.findByEmail(user.getEmail());
        if(userr.isPresent()) throw new ErrorExceptionParam(ConstantsDomain.EMAIL_EXIST);

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
