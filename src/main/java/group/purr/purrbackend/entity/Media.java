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
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(nullable = false)
    private String url;

//    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
//    private Integer category;

    /**
     * the category of file, like image
     */
    private String fileCategory;

    /**
     * the type of file, like png/jpg
     */
    private String fileType;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer host;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date createTime;

    @Column(nullable = false)
    private String name;

    /**
     * kb
     */
    private String size;

    private Integer imageHeight;

    private Integer imageWidth;

    private String thumbnailPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Media media = (Media) o;
        return Objects.equals(ID, media.ID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
