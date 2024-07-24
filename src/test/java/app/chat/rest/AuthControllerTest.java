package app.chat.rest;

import app.chat.auth.AuthService;
import app.chat.auth.JwtService;
import app.chat.builder.dto.CreateUserBuilderDTO;
import app.chat.domain.User;
import app.chat.domain.vo.CreateUserDTO;
import app.chat.domain.vo.LoginResponse;
import app.chat.domain.vo.LoginUserDTO;
import app.chat.repository.UserRepository;
import app.chat.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static app.chat.utils.UtilTest.writeValueAsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;



    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    JwtService jwtService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }


    @Test
    public void shouldCreateUserAndReturnCreatedStatus() throws Exception {
        CreateUserDTO request = CreateUserBuilderDTO.buildCreateUserDTODefault().build();
        String expectedJson = writeValueAsString(request);

        doNothing().when(authService).createUser(request);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(expectedJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        verify(authService, times(1)).createUser(request);
    }

    @Test
    public void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        CreateUserDTO request = CreateUserBuilderDTO.buildCreateUserDTODefault().build();
        request.setName("");
        String expectedJson = writeValueAsString(request);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(expectedJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenEmailIsBlank() throws Exception {
        CreateUserDTO request = CreateUserBuilderDTO.buildCreateUserDTODefault().build();
        request.setEmail("");
        String expectedJson = writeValueAsString(request);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(expectedJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnTokenWhenUserExists() throws Exception {
        LoginUserDTO request = new LoginUserDTO();
        request.setEmail("a@a.com");
        request.setPassword("123456");
        String body = writeValueAsString(request);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("token");
        loginResponse.setExpiresIn(1000L);
        String response = writeValueAsString(loginResponse);

        when(authService.authenticate(request)).thenReturn(new User());
        when(jwtService.generateToken(any())).thenReturn("token");
        when(jwtService.getExpirationTime()).thenReturn(1000L);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        verify(authService, times(1)).authenticate(request);
        verify(jwtService, times(1)).generateToken(any());
        verify(jwtService, times(1)).getExpirationTime();
    }
}
