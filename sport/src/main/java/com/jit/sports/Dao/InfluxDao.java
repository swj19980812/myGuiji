package com.jit.sports.Dao;

import com.alibaba.fastjson.JSONObject;
import com.jit.sports.extra.MyLatLngPoint;
import com.jit.sports.extra.SpeedElevation;
import org.influxdb.dto.QueryResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xushangyu on 2019/8/9.
 */
public interface InfluxDao {
    void writeSportInfoIntoDB(String sportTag, double longitude, double latitude, double altitude, double speed,
                              double azimuth, double pitch, double roll, double accelerated_x, double accelerated_y,
                              double accelerated_z, int steps);
    void insertLocationProcessedMsg(String sportTag, double longitude,double latitude);
    void insertspeedAltitudeProcessedMsg(String sportTag, long time, double speed,double altitude);
    JSONObject getProcessedMsgByTag(String sportTag);
    JSONObject getSportDetailByTag(String sportTag);
    List<MyLatLngPoint> getSprace(String sportTag);
    List<Double> getOneValue(String sportTag, String key);
    List<SpeedElevation> getSpeedElevation(String sportTag);


}
