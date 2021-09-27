package group.purr.purrbackend.entity;

import lombok.*;
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

    private String url;

    @Column(columnDefinition = "TINYTEXT")
    private String name;

    private String linkName;

    private String backgroundUrl;

    @Column(length = 25)
    private String target;

    private String description;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer visitCount;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer citeCount;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date createTime;

    @Column(columnDefinition = "DATETIME")
    private Date updateTime;

    @Column(columnDefinition = "DATETIME")
    private Date deleteTime;

    private String linkRel;

    private String linkRss;

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer color;

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
