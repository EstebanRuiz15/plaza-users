package com.restaurant.users.infraestructur.driving_http.controllers;

import com.restaurant.users.domain.interfaces.IUserService;
import com.restaurant.users.infraestructur.driving_http.dtos.Security.AuthenticationRequest;
import com.restaurant.users.infraestructur.driving_http.dtos.Security.AuthenticationResponse;
import com.restaurant.users.infraestructur.driving_http.dtos.request.UserRequestDto;
import com.restaurant.users.infraestructur.driving_http.mappers.IUserRequestMapper;
import com.restaurant.users.infraestructur.security_config.AuthenticationService;
import com.restaurant.users.infraestructur.util.InfraConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationService service;
    private final IUserService userService;
    private final IUserRequestMapper mappertoUser;

    @Operation(summary = "Method for login", description = "This method is for login with the email and password.\n\n" + //
            "rules:\n\n" + //
            "       - The correct username and password must be validated.\n\n" + //
            "       - The number of attempts must be unlimited.\n\n" + //
            "       - Once the session has started, it must be ensured that with that session started, each user has the permissions to perform the actions that correspond to their role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "400", description = " Invalid parameter. Possible errors:\n\n" +
                    "    - `email`: Cannot be null.\n\n" +
                    "    - `password`: cannot by null.\n\n"),
            @ApiResponse(responseCode = "401", description = " Unauthorized\n\n" +
                    "    - `credentials': credential invalid or not found user.\n\n")

    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(summary = "Method for created a client user", description = "This method is for creater a user with rol client (CLIENT)\n\n"
            + //
            "rules:\n\n" + //
            "       - encrypted password.\n\n" + //
            "       - Valid email structure must be verified.\n\n" + //
            "       - The phone number must contain a maximum of 13 characters and may contain the + symbol\n\n." +
            "       - The user will have the role client\n\n" +
            "       - The user must be of legal age.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description =
                    "    - `name`: Cannot be null.\n\n" +
                            "    - `Last name`: cannot be null.\n\n" +
                            "    - `cel phone`:Cannot be null .\n\n" +
                            "    - 'Identity': Cannot be null and only numbers.\n\n" +
                            "    - 'birth date': cannot be null or invalid date.\n\n" +
                            "    - 'email': cannot by null and valid email structure.\n\n" +
                            "    - 'Password': cannot be null and check eith some parameters.")

    })

    @PostMapping("/register")
    public ResponseEntity<String> register( @RequestBody @Valid UserRequestDto request) {
        userService.saveNewClient(mappertoUser.userRequestDtoToUser(request));
        return ResponseEntity.ok(InfraConstants.CREATE_CLIENT_SUCCES);
    }

    @Operation(summary = "Validate if user has ADMIN role", description = "This endpoint checks if the user has the 'ADMIN' role. Returns true if authenticated as ADMIN, otherwise Unauthorized. This service is intended to be used via Feign from other microservices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has ADMIN role and is authenticated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("validate/admin")
    public ResponseEntity<Boolean> validateIsAdmin(){
        return ResponseEntity.ok(Boolean.TRUE);
    }
    @Operation(summary = "Validate if user has OWNER role", description = "This endpoint checks if the user has the 'OWNER' role. Returns true if authenticated as OWNER, otherwise Unauthorized. This service is intended to be used via Feign from other microservices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has OWNER role and is authenticated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("validate/owner")
    public ResponseEntity<Boolean> validateIsOwner(){
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @Operation(summary = "Validate if user has EMPLOYEE role", description = "This endpoint checks if the user has the 'EMPLOYEE' role. Returns true if authenticated as EMPLOYEE, otherwise Unauthorized. This service is intended to be used via Feign from other microservices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has EMPLOYEE role and is authenticated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("validate/employee")
    public ResponseEntity<Boolean> validateIsEmployee(){
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @Operation(summary = "Validate if user has CLIENT role", description = "This endpoint checks if the user has the 'CLIENT' role. Returns true if authenticated as CLIENT, otherwise Unauthorized. This service is intended to be used via Feign from other microservices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has CLIENT role and is authenticated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("validate/client")
    public ResponseEntity<Boolean> validateIsClient(){
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/userId")
    public ResponseEntity<Integer> getIdUser(){
        return ResponseEntity.ok(userService.getUserId());
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(){
        Boolean response=Boolean.TRUE;
        return ResponseEntity.ok(response);
    }

}

