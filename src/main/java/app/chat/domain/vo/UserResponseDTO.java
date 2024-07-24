package app.chat.domain.vo;

import app.chat.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String name;
    private String email;
    private String ddd;
    private String phone;



    public static UserResponseDTO fromUser(User author) {
        return UserResponseDTO.builder()
                .name(author.getName())
                .email(author.getEmail())
                .ddd(author.getDdd())
                .phone(author.getPhone())
                .build();
    }

    public static List<UserResponseDTO> fromUsers(List<User> users) {
        return users.stream().map(UserResponseDTO::fromUser).toList();
    }
}
