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
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(columnDefinition = "TINYTEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date createTime;

    @Column(columnDefinition = "DATETIME")
    private Date updateTime;

    @Column(length = 50)
    private String linkName;

    private String backgroundUrl;

    @Column(columnDefinition = "TINYTEXT")
    private String author;

    @Column(columnDefinition = "INTEGER UNSIGNED")
    private Integer commentCount;

    @Column(columnDefinition = "INTEGER UNSIGNED")
    private Integer thumbCount;

    @Column(columnDefinition = "INTEGER UNSIGNED")
    private Integer shareCount;

    @Column(columnDefinition = "INTEGER UNSIGNED")
    private Integer viewCount;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer status;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer commentStatus;

    @Column(length = 20)
    private String pingStatus;

    @Column(columnDefinition = "TEXT")
    private String toPing;

    @Column(columnDefinition = "TEXT")
    private String pinged;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer isOriginal;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer isPinned;

    @Column(name = "abstract", columnDefinition = "TEXT")
    private String articleAbstract;

    @Column(columnDefinition = "DATETIME")
    private Date deleteTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return Objects.equals(ID, article.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
