package group.purr.purrbackend.entity;

import lombok.*;
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
public class MenuItemContainRelation {

    @EmbeddedId
    MenuItemContainRelationKey menuItemContainRelationKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuItemContainRelation that = (MenuItemContainRelation) o;
        return Objects.equals(menuItemContainRelationKey, that.menuItemContainRelationKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemContainRelationKey);
    }
}
