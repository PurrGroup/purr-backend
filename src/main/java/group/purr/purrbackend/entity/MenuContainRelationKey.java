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
@Embeddable
public class MenuContainRelationKey implements Serializable {
    private Long menuID;
    private Long menuItemID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuContainRelationKey that = (MenuContainRelationKey) o;
        return Objects.equals(menuID, that.menuID)
                && Objects.equals(menuItemID, that.menuItemID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuID, menuItemID);
    }
}
