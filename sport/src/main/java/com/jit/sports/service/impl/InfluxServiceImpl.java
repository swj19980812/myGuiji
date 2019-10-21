package com.jit.sports.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jit.sports.Dao.InfluxDao;
import com.jit.sports.extra.MyLatLngPoint;
import com.jit.sports.extra.SpeedElevation;
import com.jit.sports.service.InfluxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InfluxServiceImpl implements InfluxService {

    @Resource
    InfluxDao influxDao;

    @Override
    public void writeSportInfoIntoDB(String sportTag, double longitude, double latitude, double altitude,
                                     double speed, double azimuth, double pitch, double roll,
                                     double accelerated_x, double accelerated_y, double accelerated_z, int steps) {
        influxDao.writeSportInfoIntoDB(sportTag, longitude, latitude, altitude, speed, azimuth, pitch, roll,
                accelerated_x, accelerated_y, accelerated_z, steps);
    }

    @Override
    public void insertLocationProcessedMsg(String sportTag, double longitude, double latitude) {
        influxDao.insertLocationProcessedMsg(sportTag, longitude, latitude);
    }

    @Override
    public void insertspeedAltitudeProcessedMsg(List<SpeedElevation> res, String sportsTag) {
//        influxDao.insertspeedAltitudeProcessedMsg(sportTag, time, speed, altitude);
        for(int i=0;i<res.size();i++)
        {
            influxDao.insertspeedAltitudeProcessedMsg(sportsTag,timeToLong(res.get(i).getTime())
                    ,res.get(i).getSpeed()
                    ,res.get(i).getElevation());
        }
    }
    public static Long timeToLong(String time)
    {

        time = time.replace("Z", " UTC");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = null;
        try {
            d = format1.parse(time);
        } catch (ParseException e) {
            long t=0;
            return t;
        }
        long t = d.getTime();
        return t;
    }
    @Override
    public JSONObject getProcessedMsgByTag(String sportTag) {
        return influxDao.getProcessedMsgByTag(sportTag);
    }

    @Override
    public JSONObject getSportDetailByTag(String sportTag) {
        return influxDao.getSportDetailByTag(sportTag);
    }

    @Override
    public List<MyLatLngPoint> getSprace(String sportTag) {
        return influxDao.getSprace(sportTag);
    }

    @Override
    public List<Double> getOneValue(String sportTag, String key) {
        return influxDao.getOneValue(sportTag, key);
    }

    @Override
    public List<SpeedElevation> getSpeedElevation(String sportTag) {
        return influxDao.getSpeedElevation(sportTag);
    }
}
