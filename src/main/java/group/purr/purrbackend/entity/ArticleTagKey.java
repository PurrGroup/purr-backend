package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Embeddable
public class ArticleTagKey implements Serializable {

    private Integer articleID;
    private Integer tagID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ArticleTagKey that = (ArticleTagKey) o;
        return Objects.equals(articleID, that.articleID)
                && Objects.equals(tagID, that.tagID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleID, tagID);
    }
}
