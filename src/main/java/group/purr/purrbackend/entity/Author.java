package group.purr.purrbackend.entity;

import lombok.*;
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
@EqualsAndHashCode
public class Author {
    @Id
    private String optionKey;

    @Column(nullable = false)
    private String optionValue;
}
