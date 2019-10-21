package com.jit.sports.service;

import com.alibaba.fastjson.JSONObject;
import com.jit.sports.extra.MyLatLngPoint;
import com.jit.sports.extra.SpeedElevation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InfluxService {
    void writeSportInfoIntoDB(String sportTag, double longitude, double latitude, double altitude, double speed,
                              double azimuth, double pitch, double roll, double accelerated_x, double accelerated_y,
                              double accelerated_z, int steps);
    void insertLocationProcessedMsg(String sportTag, double longitude,double latitude);
    void insertspeedAltitudeProcessedMsg(List<SpeedElevation> res, String sportsTag);
    JSONObject getProcessedMsgByTag(String sportTag);
    JSONObject getSportDetailByTag(String sportTag);
    List<MyLatLngPoint> getSprace(String sportTag);
    List<Double> getOneValue(String sportTag, String key);
    List<SpeedElevation> getSpeedElevation(String sportTag);
}
