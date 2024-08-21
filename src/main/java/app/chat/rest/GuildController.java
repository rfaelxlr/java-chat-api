package app.chat.rest;

import app.chat.domain.vo.CreateChannelDTO;
import app.chat.domain.vo.CreateGuildDTO;
import app.chat.domain.vo.MessageDTO;
import app.chat.service.GuildService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guilds")
@AllArgsConstructor
public class GuildController {

    private final GuildService guildService;

    @GetMapping
    public ResponseEntity<?> getAllAvailableGuilds() {
        return ResponseEntity.ok().body(guildService.listAllAvailable());
    }

    @PostMapping
    public ResponseEntity<?> createGuild(@RequestBody @Valid CreateGuildDTO createGuildDTO) {
        return ResponseEntity.ok().body(guildService.createGuild(createGuildDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGuild(@PathVariable Long id) {
        return ResponseEntity.ok().body(guildService.getGuild(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuild(@PathVariable Long id) {
        guildService.deleteGuild(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> enterGuild(@PathVariable Long id) {
        return ResponseEntity.ok().body(guildService.enterGuild(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<?> getUsersGuild(@PathVariable Long id) {
        return ResponseEntity.ok().body(guildService.getUsersGuild(id));
    }

    @PostMapping("/{id}/channels")
    public ResponseEntity<?> createChannel(@PathVariable Long id, @RequestBody CreateChannelDTO createChannelDTO) {
        return ResponseEntity.ok().body(guildService.createChannel(id, createChannelDTO));
    }

    @DeleteMapping("/{guildId}/channels/{channelId}")
    public ResponseEntity<?> createChannel(@PathVariable Long guildId, @PathVariable Long channelId) {
        guildService.deleteChannel(guildId, channelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{guildId}/channels/{channelId}")
    public ResponseEntity<?> enterLeaveChannel(@PathVariable Long guildId, @PathVariable Long channelId, @RequestParam(defaultValue = "true") boolean enter) {
        return ResponseEntity.ok().body(guildService.enterChannel(guildId, channelId, enter));
    }

    @GetMapping("/{guildId}/channels/{channelId}/messages")
    public ResponseEntity<?> getChannelMessages(@PathVariable Long guildId, @PathVariable Long channelId) throws Exception {
        return ResponseEntity.ok().body(guildService.getChannelMessages(channelId));
    }

    @PostMapping("/{guildId}/channels/{channelId}/messages")
    public ResponseEntity<?> sendMessageToChannel(@PathVariable Long guildId, @PathVariable Long channelId, @RequestBody MessageDTO message) throws Exception {
        return ResponseEntity.ok().body(guildService.sendMessage(channelId, message));
    }

    @DeleteMapping("/{guildId}/channels/{channelId}/messages/{messageId}")
    public ResponseEntity<?> sendMessageToChannel(@PathVariable Long guildId, @PathVariable Long channelId, @PathVariable Long messageId) throws Exception {
        guildService.deleteMessage(channelId, messageId);
        return ResponseEntity.ok().build();
    }
}
