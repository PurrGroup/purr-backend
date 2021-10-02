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
@DynamicInsert
@DynamicUpdate
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false, name = "post_id", columnDefinition = "BIGINT UNSIGNED")
    private Long postID;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer postCategory;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String authorName;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer isAnonymous;

    @Column(name = "author_QQ", length = 15)
    private String authorQQ;

    private String authorUrl;

    @Column(length = 100)
    private String authorEmail;

    @Column(nullable = false, name = "author_IP")
    private String authorIP;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date date;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer approved;

    @Column(nullable = false)
    private String userAgent;

    @Column(name = "parent_id", columnDefinition = "BIGINT UNSIGNED")
    private Long parentID;

    @Column(columnDefinition = "TINYTEXT")
    private String parentAuthorName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(ID, comment.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
