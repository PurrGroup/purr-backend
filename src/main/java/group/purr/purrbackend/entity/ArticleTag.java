package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ArticleTag {
    @EmbeddedId
    ArticleTagKey articleTagKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ArticleTag that = (ArticleTag) o;
        return Objects.equals(articleTagKey, that.articleTagKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleTagKey);
    }
}
