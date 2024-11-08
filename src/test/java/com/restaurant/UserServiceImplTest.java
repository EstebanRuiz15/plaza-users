package com.restaurant;


import com.restaurant.users.domain.exception.ErrorExceptionParam;
import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.model.RoleEnum;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.domain.services.UserServiceImpl;
import com.restaurant.users.domain.utils.ConstantsDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserPersistencePort persistencePort;

    @InjectMocks
    private UserServiceImpl userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setName("John");
        validUser.setLastName("Doe");
        validUser.setDocument(123456789);
        validUser.setCelPhone("+1234567890");

        Calendar birthDate = Calendar.getInstance();
        birthDate.add(Calendar.YEAR, -25);
        validUser.setBirthDay(birthDate.getTime());

        validUser.setPassword("Password123!");
        validUser.setEmail("john.doe@example.com");
    }

    @Test
    void saveUserOwner_WithValidUser_ShouldSaveSuccessfully() {

        userService.saveUserOwner(validUser);

        assertEquals(RoleEnum.OWNER, validUser.getRol());
        verify(persistencePort).saveUser(validUser);
    }

    @Test
    void saveUserOwner_WithInvalidPassword_ShouldThrowException() {

        validUser.setPassword("weak");

        ErrorExceptionParam exception = assertThrows(
                ErrorExceptionParam.class,
                () -> userService.saveUserOwner(validUser)
        );
        assertEquals(ConstantsDomain.PASSWORD_INVALID, exception.getMessage());
        verify(persistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void saveUserOwner_WithAgeLessThan10_ShouldThrowException() {

        Calendar youngAge = Calendar.getInstance();
        youngAge.add(Calendar.YEAR, -5);
        validUser.setBirthDay(youngAge.getTime());

        ErrorExceptionParam exception = assertThrows(
                ErrorExceptionParam.class,
                () -> userService.saveUserOwner(validUser)
        );
        assertEquals(ConstantsDomain.ERROR_MESSAGE_BIRTHDATE, exception.getMessage());
        verify(persistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void saveUserOwner_WithAgeMoreThan80_ShouldThrowException() {

        Calendar oldAge = Calendar.getInstance();
        oldAge.add(Calendar.YEAR, -81);
        validUser.setBirthDay(oldAge.getTime());

        ErrorExceptionParam exception = assertThrows(
                ErrorExceptionParam.class,
                () -> userService.saveUserOwner(validUser)
        );
        assertEquals(ConstantsDomain.ERROR_MESSAGE_BIRTHDATE, exception.getMessage());
        verify(persistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void saveUserOwner_WithAgeExactly10_ShouldSaveSuccessfully() {

        Calendar tenYearsOld = Calendar.getInstance();
        tenYearsOld.add(Calendar.YEAR, -10);
        validUser.setBirthDay(tenYearsOld.getTime());

        userService.saveUserOwner(validUser);

        assertEquals(RoleEnum.OWNER, validUser.getRol());
        verify(persistencePort).saveUser(validUser);
    }

    @Test
    void saveUserOwner_WithAgeExactly80_ShouldSaveSuccessfully() {
        Calendar eightyYearsOld = Calendar.getInstance();
        eightyYearsOld.add(Calendar.YEAR, -80);
        validUser.setBirthDay(eightyYearsOld.getTime());
        userService.saveUserOwner(validUser);

        assertEquals(RoleEnum.OWNER, validUser.getRol());
        verify(persistencePort).saveUser(validUser);
    }



    @Test
    void getUser_WithExistingId_ShouldReturnUser() {

        Integer userId = 1;
        when(persistencePort.findByUserID(userId)).thenReturn(validUser);

        User result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(validUser, result);
        verify(persistencePort).findByUserID(userId);
    }

    @Test
    void getUser_WithNonExistingId_ShouldReturnNull() {
        Integer userId = 999;
        when(persistencePort.findByUserID(userId)).thenReturn(null);

        User result = userService.getUser(userId);

        assertNull(result);
        verify(persistencePort).findByUserID(userId);
    }
    @Test
    void testSaveNewClient_Success() {
        when(persistencePort.findByEmail(validUser.getEmail())).thenReturn(Optional.empty());

        userService.saveNewClient(validUser);

        verify(persistencePort, times(1)).saveUser(validUser);
    }

    @Test
    void testSaveNewClient_EmailAlreadyExists() {
        when(persistencePort.findByEmail(validUser.getEmail())).thenReturn(Optional.of(validUser));

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            userService.saveNewClient(validUser);
        });
        assertEquals("the email is al ready exist", exception.getMessage());
    }

    @Test
    void testSaveNewClient_InvalidPassword() {
        validUser.setPassword("123");

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            userService.saveNewClient(validUser);
        });
        assertEquals("The password must have at least 8 characters, a capital letter and a number", exception.getMessage());
    }

    @Test
    void testSaveNewClient_AgeLessThanTen() {
        Calendar birthDate = Calendar.getInstance();
        birthDate.add(Calendar.YEAR, -5);
        validUser.setBirthDay(birthDate.getTime());

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            userService.saveNewClient(validUser);
        });
        assertEquals("Invalid date of birth", exception.getMessage());
    }

    @Test
    void testSaveNewClient_AgeGreaterThanHundred() {
        Calendar birthDate = Calendar.getInstance();
        birthDate.add(Calendar.YEAR, -105);
        validUser.setBirthDay(birthDate.getTime());

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            userService.saveNewClient(validUser);
        });
        assertEquals("Invalid date of birth", exception.getMessage());
    }
}