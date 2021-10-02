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

/**
 * @author whalien
 * @since 2021-09-27 20:00
 */
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@DynamicUpdate
@DynamicInsert
public class LockdownIP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lockdown_id", columnDefinition = "BIGINT UNSIGNED")
    private Long lockdownID;

    @Column(columnDefinition = "DATETIME NOT NULL")
    private Date lockdownDate;

    @Column(columnDefinition = "DATETIME")
    private Date releaseDate;

    @Column(name = "lockdown_IP", nullable = false)
    private String lockdownIP;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LockdownIP that = (LockdownIP) o;
        return Objects.equals(lockdownID, that.lockdownID);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
