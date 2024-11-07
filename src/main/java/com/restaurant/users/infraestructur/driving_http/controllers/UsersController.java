package com.restaurant.users.infraestructur.driving_http.controllers;

import com.restaurant.users.domain.interfaces.IUserService;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.infraestructur.driving_http.dtos.request.UserRequestDto;
import com.restaurant.users.infraestructur.driving_http.dtos.response.UserResponseDto;
import com.restaurant.users.infraestructur.driving_http.mappers.IUserRequestMapper;
import com.restaurant.users.infraestructur.driving_http.mappers.IUserResponseMapper;
import com.restaurant.users.infraestructur.util.InfraConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final IUserService service;
    private final IUserRequestMapper mapper;
    private final IUserResponseMapper mapperResponse;

    @Operation(
            summary = "Create a new user",
            description = "This endpoint allows creating a new user owner. The owner information must be provided in the request body. A successful creation will return HTTP status 201 and a success message."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request due to invalid user input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/")
    public ResponseEntity<String> addProperty(@Valid @RequestBody UserRequestDto request) {
        service.saveUserOwner(mapper.userRequestDtoToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(InfraConstants.CREATE_OWNER_SUCCES);
    }

    @Operation(
            summary = "Get user by ID",
            description = "This endpoint retrieves the user details by the user ID provided as a query parameter. Returns user details including their role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User details fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request due to invalid user ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found with the provided ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/getUser")
    public ResponseEntity<UserResponseDto> getUserById(@RequestParam Integer userId) {
        User user=service.getUser(userId);
        UserResponseDto response=mapperResponse.toUserResponse(user);
        if(user != null)response.setRol(user.getRol().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}