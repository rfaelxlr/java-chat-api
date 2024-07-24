package app.chat.repository;

import app.chat.domain.Guild;
import app.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
    List<Guild> findAllByActive(boolean active);

    Optional<Guild> findByIdAndAuthor(Long id,User user);
}
