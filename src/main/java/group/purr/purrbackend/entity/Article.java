package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
@DynamicInsert
public class Article {

    @Id
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

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer status;

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer commentStatus;

    @Column(length = 20)
    private String pingStatus;

    @Column(columnDefinition = "TEXT")
    private String toPing;

    @Column(columnDefinition = "TEXT")
    private String pinged;

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer isOriginal;

    @Column(columnDefinition = "TINYINT UNSIGNED")
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
