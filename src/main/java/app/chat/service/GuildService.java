package app.chat.service;

import app.chat.auth.AuthService;
import app.chat.domain.Channel;
import app.chat.domain.ChannelUser;
import app.chat.domain.Guild;
import app.chat.domain.GuildUser;
import app.chat.domain.Message;
import app.chat.domain.User;
import app.chat.domain.vo.CreateChannelDTO;
import app.chat.domain.vo.CreateGuildDTO;
import app.chat.domain.vo.GuildResponseDTO;
import app.chat.domain.vo.MessageDTO;
import app.chat.domain.vo.MessageResponseDTO;
import app.chat.domain.vo.UserResponseDTO;
import app.chat.repository.ChannelRepository;
import app.chat.repository.ChannelUserRepository;
import app.chat.repository.GuildRepository;
import app.chat.repository.GuildUserRepository;
import app.chat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GuildService {
    private final GuildRepository guildRepository;
    private final GuildUserRepository guildUserRepository;
    private final ChannelRepository channelRepository;
    private final ChannelUserRepository channelUserRepository;
    private final MessageRepository messageRepository;
    private final AuthService authService;

    public List<GuildResponseDTO> listAllAvailable() {
        return guildRepository.findAllByActive(true).stream().map(GuildResponseDTO::fromGuild).toList();
    }

    public CreateGuildDTO createGuild(CreateGuildDTO createGuildDTO) {
        Guild guild = Guild.builder()
                .name(createGuildDTO.getName())
                .author(authService.getAuthenticatedUser())
                .size(1)
                .build();
        guildRepository.save(guild);
        return createGuildDTO;
    }

    public GuildResponseDTO getGuild(Long id) {
        Guild guild = guildRepository.findById(id).orElseThrow();
        return GuildResponseDTO.fromGuild(guild);
    }

    public void deleteGuild(Long id) {
        Guild guild = guildRepository.findByIdAndAuthor(id, authService.getAuthenticatedUser()).orElseThrow();
        guild.delete();
        guildRepository.save(guild);
    }

    public GuildResponseDTO enterGuild(Long id) {
        Guild guild = guildRepository.findById(id).orElseThrow();
        User user = authService.getAuthenticatedUser();
        if (guild.getAuthor().getId().equals(user.getId())) {
            return GuildResponseDTO.fromGuild(guild);
        }

        if (guildUserRepository.findByGuildAndUser(guild, user).isPresent()) {
            return GuildResponseDTO.fromGuild(guild);
        }
        guild.setSize(guild.getSize() + 1);
        guildRepository.save(guild);
        GuildUser guildUser = GuildUser.builder()
                .guild(guild)
                .user(user)
                .build();
        guildUserRepository.save(guildUser);
        return GuildResponseDTO.fromGuild(guild);

    }

    public List<UserResponseDTO> getUsersGuild(Long id) {
        Guild guild = guildRepository.findById(id).orElseThrow();
        List<UserResponseDTO> list = new java.util.ArrayList<>(guildUserRepository.findByGuild(guild).stream().map(GuildUser::getUser).map(UserResponseDTO::fromUser).toList());
        list.add(UserResponseDTO.fromUser(guild.getAuthor()));
        return list;
    }

    public CreateChannelDTO createChannel(Long id, CreateChannelDTO createChannelDTO) {
        Guild guild = guildRepository.findByIdAndAuthor(id, authService.getAuthenticatedUser()).orElseThrow();

        Channel channel = channelRepository.save(Channel.builder()
                .name(createChannelDTO.getName())
                .size(1)
                .guild(guild)
                .build());

        channelUserRepository.save(ChannelUser.builder()
                .channel(channel)
                .user(authService.getAuthenticatedUser())
                .build());

        return createChannelDTO;
    }

    @Transactional
    public void deleteChannel(Long guildId, Long channelId) {
        Guild guild = guildRepository.findByIdAndAuthor(guildId, authService.getAuthenticatedUser()).orElseThrow();
        Channel channel = channelRepository.findById(channelId).orElseThrow();
        channelUserRepository.deleteByChannel(channel);
        channelRepository.deleteByIdAndGuild(channelId, guild);
    }

    @Transactional
    public CreateChannelDTO enterChannel(Long guildId, Long channelId, boolean enter) {
        Channel channel = channelRepository.findById(channelId).orElseThrow();
        if (channel.getGuild().getAuthor().getId().equals(authService.getAuthenticatedUser().getId())) {
            return CreateChannelDTO.fromChannel(channel);
        }

        if (enter) {
            if (channelUserRepository.findByChannelAndUser(channel, authService.getAuthenticatedUser()).isPresent()) {
                return CreateChannelDTO.fromChannel(channel);
            }
            channelUserRepository.save(ChannelUser.builder()
                    .channel(channel)
                    .user(authService.getAuthenticatedUser())
                    .build());
            channel.setSize(channel.getSize() + 1);
        } else {
            if (channelUserRepository.findByChannelAndUser(channel, authService.getAuthenticatedUser()).isEmpty()) {
                return CreateChannelDTO.fromChannel(channel);
            }
            channelUserRepository.deleteByChannelAndUser(channel, authService.getAuthenticatedUser());
            channel.setSize(channel.getSize() - 1);
        }
        channelRepository.save(channel);
        return CreateChannelDTO.fromChannel(channel);
    }

    public List<MessageResponseDTO> getChannelMessages(Long channelId) throws Exception {
        User user = authService.getAuthenticatedUser();
        if (!channelUserRepository.userInChannel(user.getId(), channelId)) {
            throw new Exception("You are not in this channel");
        }
        return messageRepository.findByChannelIdAndGuildAndUser(user.getId(), channelId).stream().map(MessageResponseDTO::fromMessage).toList();
    }


    public MessageResponseDTO sendMessage(Long channelId, MessageDTO messageDTO) throws Exception {
        User user = authService.getAuthenticatedUser();
        if (!channelUserRepository.userInChannel(user.getId(), channelId)) {
            throw new Exception("You are not in this channel");
        }
        Message message = messageRepository.save(Message.builder()
                .content(messageDTO.getContent())
                .channel(channelRepository.findById(channelId).orElseThrow())
                .author(user)
                .build());
        return MessageResponseDTO.fromMessage(message);
    }


    public void deleteMessage(Long channelId, Long messageId) throws Exception {
        User user = authService.getAuthenticatedUser();
        if (!channelUserRepository.userInChannel(user.getId(), channelId)) {
            throw new Exception("You are not in this channel");
        }

        messageRepository.deleteMessage(messageId, user.getId());
    }
}

