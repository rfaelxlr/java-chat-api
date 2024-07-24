package app.chat.domain.vo;

import app.chat.domain.Guild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuildResponseDTO {
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private boolean active;
    private String name;
    private Integer size;
    private UserResponseDTO author;
    private List<ChannelResponseDTO> channels;

    public static GuildResponseDTO fromGuild(Guild guild) {
        return GuildResponseDTO.builder()
                .creationDate(guild.getCreationDate())
                .modifiedDate(guild.getModifiedDate())
                .active(guild.isActive())
                .name(guild.getName())
                .size(guild.getSize())
                .author(UserResponseDTO.fromUser(guild.getAuthor()))
                .channels(ChannelResponseDTO.fromChannels(guild.getChannels()))
                .build();
    }
}
