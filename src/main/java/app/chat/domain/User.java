package app.chat.domain;

import app.chat.domain.vo.CreateUserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 100)
    @Size(min = 3, max = 100)
    private String name;
    @Email
    @Column(nullable = false, length = 150)
    private String email;
    @Column(nullable = false, length = 32)
    private String password;
    @Column(nullable = false, length = 2)
    private String ddd;
    @Column(nullable = false, length = 9)
    private String phone;
    private boolean admin = false;

    public User fromDTO(CreateUserDTO createUserDTO, PasswordEncoder passwordEncoder) {
        this.name = createUserDTO.getName();
        this.email = createUserDTO.getEmail();
        this.password = passwordEncoder.encode(createUserDTO.getPassword());
        this.ddd = createUserDTO.getDdd();
        this.phone = createUserDTO.getPhone();
        this.admin = false;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
