package com.restaurant.users.aplication;

import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class BeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserServiceImpl getUserServiceImpl(IUserPersistencePort iUserPersistencePort){
        return new UserServiceImpl(iUserPersistencePort);
    }


}
