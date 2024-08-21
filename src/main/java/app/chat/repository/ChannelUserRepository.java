package app.chat.repository;

import app.chat.domain.Channel;
import app.chat.domain.ChannelUser;
import app.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {
    Optional<ChannelUser> findByChannelAndUser(Channel channel, User authenticatedUser);

    void deleteByChannelAndUser(Channel channel, User authenticatedUser);

    void deleteByChannel(Channel channel);

    @Query(value = "select exists (select 1 from channel_users cu where user_id = ?1 and channel_id = ?2)", nativeQuery = true)
    boolean userInChannel(Long userId, Long channelId);
}
