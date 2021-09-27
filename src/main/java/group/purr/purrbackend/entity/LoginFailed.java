package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author whalien
 * @since 2021-09-27 19:20
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode
public class LoginFailed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="login_attempt_id", columnDefinition = "BIGINT UNSIGNED")
    private Long loginAttemptID;

    @Column(columnDefinition = "DATETIME")
    private String name;

    @Column(name = "login_attempt_IP")
    private String loginAttemptIP;
}
