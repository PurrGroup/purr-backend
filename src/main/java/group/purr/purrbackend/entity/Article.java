package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode
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

    @Column(length = 255)
    private String linkName;

    @Column(length = 25, nullable = false)
    private String target;

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

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer isPinned;

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer isRecommended;

    @Column(name = "abstract", columnDefinition = "TEXT")
    private String articleAbstract;

    @Column(columnDefinition = "DATETIME")
    private Date deleteTime;

    @Column(columnDefinition = "TINYINT")
    private Integer copyright;

    @Column(columnDefinition = "TINYINT")
    private Integer contract;

    @Column(columnDefinition = "TEXT")
    private String copyrightInfo;

}
