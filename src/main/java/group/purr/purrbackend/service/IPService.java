package group.purr.purrbackend.service;

import java.util.Date;

public interface IPService {
    Boolean isIPLockdown(String IP);

    void lockdownIP(String IP, Date lockdownDate, Date releaseDate);

    void loginFailed(String IP, Date date);

    void checkLockdownStatus(String IP, Date date);
}
