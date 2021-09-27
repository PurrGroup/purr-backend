package group.purr.purrbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author admin
 * @since 2021-09-27 20:20
 */
@RequiredArgsConstructor
@Getter@Setter@ToString
@DynamicInsert
@DynamicUpdate
@Entity
public class Author {
    @Id
    private String optionKey;

    @Column(nullable = false)
    private String optionValue;
}
