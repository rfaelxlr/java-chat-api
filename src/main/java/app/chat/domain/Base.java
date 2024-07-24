package app.chat.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Base {
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private boolean active = true;

    @PrePersist
    protected void onCreate() {
        setCreationDate(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        setModifiedDate(LocalDateTime.now());
    }
}
