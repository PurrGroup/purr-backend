package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Blogmeta {

    @Id
    private String optionKey;

    private String optionValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Blogmeta blogmeta = (Blogmeta) o;
        return Objects.equals(optionKey, blogmeta.optionKey);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
