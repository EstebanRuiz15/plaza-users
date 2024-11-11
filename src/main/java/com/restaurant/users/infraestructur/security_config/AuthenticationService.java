package com.restaurant.users.infraestructur.security_config;

import com.restaurant.users.aplication.exception.ErrorNotAuthentication;
import com.restaurant.users.domain.interfaces.IUserPersistencePort;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.infraestructur.driving_http.dtos.Security.AuthenticationRequest;
import com.restaurant.users.infraestructur.driving_http.dtos.Security.AuthenticationResponse;
import com.restaurant.users.infraestructur.security_config.jwt_configuration.JwtService;
import com.restaurant.users.infraestructur.util.InfraConstants;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserPersistencePort repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user =  repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generate(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }catch(AuthenticationException e) {
                throw new ErrorNotAuthentication(InfraConstants.ERROR_MESSAGE_UNAUTHORIZED);
            }
    }
}
