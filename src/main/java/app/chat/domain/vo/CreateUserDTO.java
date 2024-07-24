package app.chat.domain.vo;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDTO {
    @Size(min = 3, max = 100)
    @NotEmpty
    private String name;
    @Email
    @NotEmpty
    @Size(min = 5, max = 100)
    private String email;
    @NotEmpty
    @Size(min = 8, max = 100)
    private String password;
    @NotEmpty
    @Size(min = 2, max = 2)
    private String ddd;
    @NotEmpty
    @Size(min = 9, max = 9)
    private String phone;
}
