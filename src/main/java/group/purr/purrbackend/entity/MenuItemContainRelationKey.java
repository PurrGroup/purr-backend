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
public class MenuItemContainRelationKey implements Serializable {
    private Long menuItemID;
    private Long subMenuItemID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuItemContainRelationKey that = (MenuItemContainRelationKey) o;
        return Objects.equals(menuItemID, that.menuItemID)
                && Objects.equals(subMenuItemID, that.subMenuItemID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemID, subMenuItemID);
    }
}
