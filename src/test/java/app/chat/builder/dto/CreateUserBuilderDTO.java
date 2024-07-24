package app.chat.builder.dto;

import app.chat.domain.vo.CreateUserDTO;

public class CreateUserBuilderDTO {
    private CreateUserDTO createUserDTO;

    public static CreateUserBuilderDTO buildCreateUserDTODefault() {
        CreateUserBuilderDTO createUserBuilderDTO = new CreateUserBuilderDTO();
        createUserBuilderDTO.createUserDTO = CreateUserDTO.builder()
                .name("Rafael Kakashi")
                .email("kakashi@hotmail.com")
                .ddd("11")
                .phone("999999999")
                .password("k1a2k3a4s5h6i#")
                .build();
        return createUserBuilderDTO;
    }

    public CreateUserDTO build() {
        return createUserDTO;
    }
}
