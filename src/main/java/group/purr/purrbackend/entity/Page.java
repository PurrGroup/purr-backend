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
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date createTime;

    @Column(columnDefinition = "DATETIME")
    private Date updateTime;

    @Column(columnDefinition = "DATETIME")
    private Date deleteTime;

    @Column(columnDefinition = "TINYTEXT")
    private String name;

    @Column(columnDefinition = "TINYTEXT")
    private String urlName;

    private String description;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer commentStatus;

    private String backgroundUrl;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer commentCount;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer thumbCount;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer shareCount;

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    private Integer viewCount;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer status;

    @Column(length = 20)
    private String pingStatus;

    @Column(columnDefinition = "TEXT")
    private String toPing;

    @Column(columnDefinition = "TEXT")
    private String pinged;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Page page = (Page) o;
        return Objects.equals(ID, page.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
