package app.chat.domain.vo;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginUserDTO {
    @Email
    private String email;
    private String password;
}
