package com.restaurant.users.aplication;

import com.restaurant.users.domain.interfaces.IServiceRestaurantFeig;
import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.services.UserServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
    public UserServiceImpl getUserServiceImpl(IUserPersistencePort iUserPersistencePort, IServiceRestaurantFeig feignClient){
        return new UserServiceImpl(iUserPersistencePort, feignClient);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservice Users")
                        .version("1.0.0")
                        .description("This services is for the clients register, and create new users of get information of the users\n\n" +
                                "And this microservice provides authentication and authorities."));
    }


}
