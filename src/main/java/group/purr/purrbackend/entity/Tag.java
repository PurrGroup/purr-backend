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
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String name;

    @Column(nullable = false)
    private String linkName;

    private String backgroundUrl;

    @Column(nullable = false, length = 25)
    private String target;

    private String description;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer visitCount;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer citeCount;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date createTime;

    @Column(columnDefinition = "DATETIME")
    private Date updateTime;

    @Column(columnDefinition = "DATETIME")
    private Date deleteTime;

    private String linkRel;

    private String linkRss;

    @Column(nullable = false)
    private String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(ID, tag.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
