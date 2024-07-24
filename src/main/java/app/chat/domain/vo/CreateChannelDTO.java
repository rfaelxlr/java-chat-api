package app.chat.domain.vo;

import app.chat.domain.Channel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChannelDTO {
    @Size(min = 3, max = 100)
    @NotEmpty
    private String name;

    public static CreateChannelDTO fromChannel(Channel channel) {
        return CreateChannelDTO.builder()
                .name(channel.getName())
                .build();
    }
}
