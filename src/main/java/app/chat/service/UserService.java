package app.chat.service;

import app.chat.domain.User;
import app.chat.domain.vo.CreateUserDTO;
import app.chat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmailAndActive(email, true).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
