package com.jit.sports.Dao;

import com.jit.sports.entry.SportInfo;
import com.jit.sports.entry.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    UserInfo login(String userName, String password);
    String existUserName(String userName);
    void reg(String userName, String password);

    //插入一次运动
    void insertSport(String sportTag, String userName, String startTime);

    void updateSport(String sportTag, String overTime,double totalDistance, double totalUp, double totalDown,
                     double averageSpeed, double maxSpeed, double maxAltitude, double minAltitude,
                     String mode, String sportTitle);

    //根据用户查找所有运动
    SportInfo[] selectSportByName(String userName);

    SportInfo selectSportByTag(String sportTag);
    //根据用户查找固定时间段的运动
    SportInfo[] selectNotOverSport(String userName);

}
