package app.chat.repository;

import app.chat.domain.Guild;
import app.chat.domain.GuildUser;
import app.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuildUserRepository extends JpaRepository<GuildUser, Long> {

    Optional<GuildUser> findByGuildAndUser(Guild guild, User user);

    List<GuildUser> findByGuild(Guild guild);
}
