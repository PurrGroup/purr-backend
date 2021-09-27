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
@DynamicUpdate
@DynamicInsert
public class Page {

    @Id
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

    @Column(columnDefinition = "INTEGER UNSIGNED")
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
}
