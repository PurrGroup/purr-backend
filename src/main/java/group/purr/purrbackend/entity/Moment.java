package group.purr.purrbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date createTime;

    @Column(columnDefinition = "DATETIME")
    private Date updateTime;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer thumbCount;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer backgroundColor;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String annotation;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer visible;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Moment moment = (Moment) o;
        return Objects.equals(ID, moment.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
