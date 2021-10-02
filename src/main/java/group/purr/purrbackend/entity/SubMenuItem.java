package group.purr.purrbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SubMenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    private String icon;

    @Column(length = 25, nullable = false)
    private String target;

    @Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long parentID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubMenuItem that = (SubMenuItem) o;
        return Objects.equals(ID, that.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
