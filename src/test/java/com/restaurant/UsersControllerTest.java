package com.restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.users.domain.interfaces.IUserService;
import com.restaurant.users.domain.model.User;
import com.restaurant.users.domain.utils.ConstantsDomain;
import com.restaurant.users.infraestructur.driving_http.dtos.request.UserRequestDto;
import com.restaurant.users.infraestructur.driving_http.dtos.response.UserResponseDto;
import com.restaurant.users.infraestructur.driving_http.mappers.IUserRequestMapper;
import com.restaurant.users.infraestructur.driving_http.mappers.IUserResponseMapper;
import com.restaurant.users.infraestructur.util.InfraConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    @MockBean
    private IUserRequestMapper userRequestMapper;

    @MockBean
    private IUserResponseMapper userResponseMapper;

    private UserRequestDto validRequest;
    private User validUser;
    private UserResponseDto validResponse;

    @BeforeEach
    void setUp() {
        validRequest = new UserRequestDto();
        validRequest.setName("John");
        validRequest.setLastName("Doe");
        validRequest.setDocument(123456789);
        validRequest.setCelPhone("+573001234567");
        validRequest.setBirthDay(new Date(90, 1, 1)); // 1990-01-01
        validRequest.setEmail("john.doe@example.com");
        validRequest.setPassword("password123");

        validUser = new User();

        validResponse = new UserResponseDto();
        validResponse.setRol("OWNER");

        when(userRequestMapper.userRequestDtoToUser(any(UserRequestDto.class))).thenReturn(validUser);
        when(userResponseMapper.toUserResponse(any(User.class))).thenReturn(validResponse);
    }

    @Test
    void createUser_WithValidRequest_ShouldReturnCreated() throws Exception {

        String requestJson = objectMapper.writeValueAsString(validRequest);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(InfraConstants.CREATE_OWNER_SUCCES));
    }

    @Test
    void createUser_WithEmptyName_ShouldReturnBadRequest() throws Exception {

        validRequest.setName("");
        String requestJson = objectMapper.writeValueAsString(validRequest);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value(ConstantsDomain.NAME_EMPTY));
    }

    @Test
    void createUser_WithInvalidName_ShouldReturnBadRequest() throws Exception {

        validRequest.setName("John123");
        String requestJson = objectMapper.writeValueAsString(validRequest);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value(ConstantsDomain.NAME_INVALID));
    }

    @Test
    void createUser_WithInvalidEmail_ShouldReturnBadRequest() throws Exception {

        validRequest.setEmail("invalid-email");
        String requestJson = objectMapper.writeValueAsString(validRequest);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value(ConstantsDomain.EMAIL_INVALID));
    }

    @Test
    void createUser_WithInvalidPassword_ShouldReturnBadRequest() throws Exception {

        validRequest.setPassword("short"); // Menos de 8 caracteres
        String requestJson = objectMapper.writeValueAsString(validRequest);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value(ConstantsDomain.PASSWORD_INVALID));
    }

    @Test
    void createUser_WithInvalidPhone_ShouldReturnBadRequest() throws Exception {

        validRequest.setCelPhone("123"); // Formato inválido
        String requestJson = objectMapper.writeValueAsString(validRequest);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.celPhone").value(ConstantsDomain.CEL_PHONE_INVALID));
    }

    @Test
    void createUser_WithFutureBirthday_ShouldReturnBadRequest() throws Exception {

        validRequest.setBirthDay(new Date(System.currentTimeMillis() + 86400000)); // Mañana
        String requestJson = objectMapper.writeValueAsString(validRequest);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.birthDay").value(ConstantsDomain.BIRTHDAY_INVALID));
    }

    @Test
    void getUser_WithValidId_ShouldReturnUser() throws Exception {

        when(userService.getUser(1)).thenReturn(validUser);

        mockMvc.perform(get("/users/getUser")
                        .param("userId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rol").value("OWNER"));
    }

    @Test
    void getUser_WithNonExistentId_ShouldReturnNull() throws Exception {

        when(userService.getUser(999)).thenReturn(null);

        mockMvc.perform(get("/users/getUser")
                        .param("userId", "999"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rol").doesNotExist());
    }
}