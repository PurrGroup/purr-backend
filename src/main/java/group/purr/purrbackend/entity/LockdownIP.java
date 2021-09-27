package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whalien
 * @since 2021-09-27 20:00
 */
@RequiredArgsConstructor
@Getter@Setter
@ToString
@Entity
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode
public class LockdownIP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lockdown_id", columnDefinition = "BIGINT UNSIGNED")
    private Long lockdownID;

    @Column(columnDefinition = "DATETIME NOT NULL")
    private Date lockdownDate;

    @Column(columnDefinition = "DATETIME")
    private Date releaseDate;

    @Column(name="lockdown_IP", nullable = false)
    private String lockdownIP;
}
