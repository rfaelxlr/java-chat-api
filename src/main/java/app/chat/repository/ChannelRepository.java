package app.chat.repository;

import app.chat.domain.Channel;
import app.chat.domain.Guild;
import app.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    void deleteByIdAndGuild(Long channelId, Guild guild);
}
