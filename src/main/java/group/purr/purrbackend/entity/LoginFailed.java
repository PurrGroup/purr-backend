package group.purr.purrbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

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
public class LoginFailed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_attempt_id", columnDefinition = "BIGINT UNSIGNED")
    private Long loginAttemptID;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private String loginAttemptDate;

    @Column(nullable = false, name = "login_attempt_IP")
    private String loginAttemptIP;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LoginFailed that = (LoginFailed) o;
        return Objects.equals(loginAttemptID, that.loginAttemptID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
