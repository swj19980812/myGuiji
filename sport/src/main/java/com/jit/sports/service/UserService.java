package com.jit.sports.service;

import com.jit.sports.entry.SportInfo;
import com.jit.sports.entry.UserInfo;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    UserInfo login(String userName, String password);
    String existUserName(String userName);
    void reg(String userName, String password);
    void insertSport(String sportTag, String userName, String startTime);

    void updateSport(String sportTag, String overTime,double totalDistance, double totalUp, double totalDown,
                     double averageSpeed, double maxSpeed, double maxAltitude, double minAltitude,
                     String mode, String sportTitle);

    SportInfo[] selectSportByName(String userName);
    SportInfo selectSportByTag(String sportTag);
    SportInfo[] selectNotOverSport(String userName);
}
