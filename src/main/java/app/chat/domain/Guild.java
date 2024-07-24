package app.chat.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "guilds")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guild extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 100)
    @Size(min = 3, max = 100)
    private String name;
    @Column(nullable = false)
    private Integer size = 1;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany
    @JoinColumn(name = "guild_id")
    private List<Channel> channels;

    public void delete() {
        setActive(false);
    }

    public void setSize(int i) {
        this.size = i;
    }
}
