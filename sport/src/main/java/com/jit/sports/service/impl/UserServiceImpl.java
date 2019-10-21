package com.jit.sports.service.impl;

import com.jit.sports.Dao.UserDao;
import com.jit.sports.entry.SportInfo;
import com.jit.sports.entry.UserInfo;
import com.jit.sports.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public UserInfo login(String userName, String password) {
        return userDao.login(userName, password);
    }

    @Override
    public String existUserName(String userName) {
        return userDao.existUserName(userName);
    }

    @Override
    public void reg(String userName, String password) {
        userDao.reg(userName, password);
    }

    @Override
    public void insertSport(String sportTag, String userName, String startTime) {
        userDao.insertSport(sportTag, userName, startTime);
    }

    @Override
    public void updateSport(String sportTag, String overTime, double totalDistance, double totalUp,
                            double totalDown, double averageSpeed, double maxSpeed, double maxAltitude,
                            double minAltitude, String mode, String sportTitle) {
        userDao.updateSport(sportTag, overTime, totalDistance, totalUp, totalDown, averageSpeed, maxSpeed,
                maxAltitude, minAltitude, mode, sportTitle);
    }

    @Override
    public SportInfo[] selectSportByName(String userName) {
        return userDao.selectSportByName(userName);
    }

    @Override
    public SportInfo selectSportByTag(String sportTag) {
        return userDao.selectSportByTag(sportTag);
    }

    @Override
    public SportInfo[] selectNotOverSport(String userName) {
        return userDao.selectNotOverSport(userName);
    }
}
