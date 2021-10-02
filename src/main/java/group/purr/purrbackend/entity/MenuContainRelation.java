package group.purr.purrbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
public class MenuContainRelation {
    @EmbeddedId
    MenuContainRelationKey menuContainRelationKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuContainRelation that = (MenuContainRelation) o;
        return Objects.equals(menuContainRelationKey, that.menuContainRelationKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuContainRelationKey);
    }
}
