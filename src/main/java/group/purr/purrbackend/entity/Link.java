package group.purr.purrbackend.entity;

import lombok.*;
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
public class Link {

    @Id
    @Column(nullable = false, name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String name;

    @Column(nullable = false)
    private String linkName;

    @Column(nullable = false)
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

    @Column(nullable = false)
    private String linkRss;
}
