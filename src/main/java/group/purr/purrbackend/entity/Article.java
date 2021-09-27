package group.purr.purrbackend.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Article {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGEND")
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

}
