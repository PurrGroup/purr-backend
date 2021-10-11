package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.entity.LockdownIP;
import group.purr.purrbackend.entity.LoginFailed;
import group.purr.purrbackend.repository.LockdownIPRepository;
import group.purr.purrbackend.repository.LoginFailedRepository;
import group.purr.purrbackend.service.IPService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class IPServiceImpl implements IPService {
    final LockdownIPRepository lockdownIPRepository;
    final LoginFailedRepository loginFailedRepository;

    public IPServiceImpl(LockdownIPRepository lockdownIPRepository, LoginFailedRepository loginFailedRepository) {
        this.lockdownIPRepository = lockdownIPRepository;
        this.loginFailedRepository = loginFailedRepository;
    }

    @Override
    public Boolean isIPLockdown(String IP){
        LockdownIP lockdownIP = lockdownIPRepository.findByLockdownIP(IP);
        Date date = new Date();

        if(lockdownIP == null)
            return false;

        if(lockdownIP.getReleaseDate().before(date))
            return false;

        return true;
    }

    @Override
    public void lockdownIP(String IP, Date lockdownDate, Date releaseDate) {
        LockdownIP lockdown = new LockdownIP();
        lockdown.setLockdownIP(IP);
        lockdown.setLockdownDate(lockdownDate);
        lockdown.setReleaseDate(releaseDate);
        lockdownIPRepository.save(lockdown);
    }

    @Override
    public void loginFailed(String IP, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginFailedDate = simpleDateFormat.format(date);

        LoginFailed loginFailed = new LoginFailed();
        loginFailed.setLoginAttemptIP(IP);
        loginFailed.setLoginAttemptDate(loginFailedDate);
        loginFailedRepository.save(loginFailed);
    }

    @Override
    public void checkLockdownStatus(String IP, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginFailedDate = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -5);
        Date beginDate = calendar.getTime();
        String beginTime = simpleDateFormat.format(beginDate);
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 5);
        Date endTime = calendar.getTime();

        Integer count = loginFailedRepository.countByLoginAttemptDateBetween(beginTime, loginFailedDate);
        if(count > 5)
            lockdownIP(IP, date, endTime);
    }


}
