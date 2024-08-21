package app.chat.repository;

import app.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select m.* from messages m join channel_users cu on cu.channel_id  = m.channel_id " +
            "where cu.user_id  = ?1 and cu.channel_id = ?2 order by creation_date desc", nativeQuery = true)
    List<Message> findByChannelIdAndGuildAndUser(Long userId, Long channelId);

    @Query(value = "delete from messages m where m.id = ?1 and m.author_id = ?2", nativeQuery = true)
    void deleteMessage(Long messageId, Long authorId);
}
