package app.chat.domain.vo;

import app.chat.domain.Channel;
import app.chat.domain.ChannelUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelResponseDTO {

    private Long id;
    private String name;
    private Integer size;
    private List<UserResponseDTO> members;
    public static List<ChannelResponseDTO> fromChannels(List<Channel> channels) {
        return channels.stream().map(ChannelResponseDTO::fromChannel).toList();

    }

    private static ChannelResponseDTO fromChannel(Channel channel) {
        return ChannelResponseDTO.builder()
                .id(channel.getId())
                .name(channel.getName())
                .size(channel.getSize())
                .members(UserResponseDTO.fromUsers(channel.getChannelUsers().stream().map(ChannelUser::getUser)
                        .collect(Collectors.toList())))
                .build();
    }
}
