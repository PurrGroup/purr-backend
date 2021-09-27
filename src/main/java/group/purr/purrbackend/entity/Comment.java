package group.purr.purrbackend.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Comment {

    @Id
    @Column(nullable = false, name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false, name = "post_id", columnDefinition = "BIGINT UNSIGNED")
    private Long postID;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer postCategory;

    @Column(columnDefinition = "TINYTEXT")
    private String author;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String authorName;

    @Column(name = "author_QQ", length = 15)
    private String authorQQ;

    private String authorUrl;

    @Column(length = 100)
    private String authorEmail;

    @Column(name = "author_IP")
    private String authorIP;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date date;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer approved;

    private String userAgent;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long parentID;
}
