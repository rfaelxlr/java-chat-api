package app.chat.rest;

import app.chat.auth.AuthService;
import app.chat.auth.JwtService;
import app.chat.domain.User;
import app.chat.domain.vo.CreateUserDTO;
import app.chat.domain.vo.LoginResponse;
import app.chat.domain.vo.LoginUserDTO;
import app.chat.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        authService.createUser(createUserDTO);
        return ResponseEntity.created(URI.create("/api/users/" + null))
                .body(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();


        return ResponseEntity.ok(loginResponse);
    }
}
