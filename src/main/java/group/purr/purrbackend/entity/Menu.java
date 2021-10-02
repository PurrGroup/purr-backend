package group.purr.purrbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long ID;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String name;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer isDefault;
}
