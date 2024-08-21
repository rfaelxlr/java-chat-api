package app.chat.domain.vo;

import app.chat.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {

    private String content;
    private UserResponseDTO author;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    public static MessageResponseDTO fromMessage(Message message) {

        return MessageResponseDTO.builder()
                .content(message.getContent())
                .author(UserResponseDTO.fromUser(message.getAuthor()))
                .creationDate(message.getCreationDate())
                .modificationDate(message.getModifiedDate())
                .build();
    }
}
