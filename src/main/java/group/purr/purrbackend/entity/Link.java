package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String name;

    @Column(nullable = false)
    private String linkName;

    private String imageUrl;

    @Column(length = 25, nullable = false)
    private String target;

    private String description;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer visitCount;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer citeCount;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date createTime;

    @Column(columnDefinition = "DATETIME")
    private Date updateTime;

    @Column(columnDefinition = "DATETIME")
    private Date deleteTime;

    @Column(nullable = false)
    private String linkRel;

    private String linkRss;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Link link = (Link) o;
        return Objects.equals(ID, link.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
