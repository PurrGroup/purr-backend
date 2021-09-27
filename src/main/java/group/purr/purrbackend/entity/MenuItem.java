package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String name;

    private String url;

    private String icon;

    @Column(length = 25, nullable = false)
    private String target;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer isParent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(ID, menuItem.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
