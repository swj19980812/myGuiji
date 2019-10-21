package com.jit.sports.Dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jit.sports.Dao.InfluxDao;
import com.jit.sports.Utils.InfluxDbUtils;
import com.jit.sports.extra.MyLatLngPoint;
import com.jit.sports.extra.SpeedElevation;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by xushangyu on 2019/8/9.
 */
@Repository
public class InfluxDaoImpl implements InfluxDao{

    @Autowired
    InfluxDbUtils influxDbUtils;

    //取出结果集中的数据
    public static JSONObject packageMsg(QueryResult results) {
        QueryResult.Result oneResult = results.getResults().get(0);
        List<QueryResult.Series> series = oneResult.getSeries();

        JSONObject res = new JSONObject();
        if(series == null) {
            return res;
        }
        for(QueryResult.Series series1 : series) {
            res.put("columns", series1.getColumns());
            res.put("values", series1.getValues());
        }
        return res;
    }

    @Override
    public void writeSportInfoIntoDB(String sportTag, double longitude, double latitude, double altitude, double speed, double azimuth, double pitch, double roll, double accelerated_x, double accelerated_y, double accelerated_z, int steps) {
        Map<String, String> tags = new HashMap<String, String>();
        Map<String, Object> fields = new HashMap<String, Object>();
        tags.put("sportTag", sportTag);
        fields.put("longitude", longitude);
        fields.put("latitude", latitude);
        fields.put("altitude", altitude);
        fields.put("speed", speed);
        fields.put("azimuth", azimuth);
        fields.put("pitch", pitch);
        fields.put("roll", roll);
        fields.put("accelerated_x", accelerated_x);
        fields.put("accelerated_y", accelerated_y);
        fields.put("accelerated_z", accelerated_z);
        fields.put("steps", steps);

//        System.out.println(influxDbUtils);
        influxDbUtils.insert("sportDetail", tags, fields, System.currentTimeMillis(),
                TimeUnit.MILLISECONDS);

        System.out.println(sportTag + "插入一条记录。");
    }

    //插入抽稀后的位置信息
    @Override
    public void insertLocationProcessedMsg(String sportTag, double longitude,double latitude){
        Map<String, String> tags = new HashMap<String, String>();
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("longitude", longitude);
        fields.put("latitude", latitude);
        tags.put("sportTag", sportTag);

        influxDbUtils.insert("locationDetail", tags, fields,System.currentTimeMillis(),
                TimeUnit.MILLISECONDS);
    }

    //插入聚合后的速度、海拔信息
    @Override
    public void insertspeedAltitudeProcessedMsg(String sportTag, long time, double speed, double altitude) {
        Map<String, String> tags = new HashMap<String, String>();
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("speed", speed);
        fields.put("altitude", altitude);
        tags.put("sportTag", sportTag);

        influxDbUtils.insert("speedAltitudeDetail", tags, fields, time, TimeUnit.MILLISECONDS);
    }

    @Override
    public JSONObject getProcessedMsgByTag(String sportTag) {
        JSONObject res = new JSONObject();
        QueryResult results = influxDbUtils
                .query("SELECT time,longitude,latitude FROM locationDetail " +
                        "where sportTag = '"+ sportTag +"'  order by time asc");
        res.put("location", packageMsg(results));
        QueryResult results2 = influxDbUtils
                .query("SELECT time,speed,altitude FROM speedAltitudeDetail " +
                        "where sportTag = '"+ sportTag +"'  order by time asc");
        res.put("speedAltitude", packageMsg(results2));
        return res;
    }

    @Override
    public JSONObject getSportDetailByTag(String sportTag) {
        QueryResult results = influxDbUtils
                .query("SELECT time,longitude,latitude,altitude,speed,steps FROM sportDetail " +
                        "where sportTag = '"+ sportTag +"'  order by time asc");
        return packageMsg(results);
    }

    @Override
    public List<MyLatLngPoint> getSprace(String sportTag) {
        QueryResult results = influxDbUtils
                .query("SELECT longitude,latitude FROM sportDetail " +
                        "where sportTag = '"+ sportTag +"'  order by time asc");

        QueryResult.Result oneResult = results.getResults().get(0);
        //List<QueryResult.Series> series = oneResult.getSeries();
        int i = 0;
        List<List<Object>> valueList = null;
        List<MyLatLngPoint>res = new ArrayList<>();
        if (oneResult.getSeries() != null)
        {
            valueList = oneResult.getSeries().stream().map(QueryResult.Series::getValues)
                    .collect(Collectors.toList()).get(0);
            if (valueList != null && valueList.size() > 0) {
                for (List<Object> value : valueList) {

                    MyLatLngPoint a = new MyLatLngPoint();
                    String longitude = value.get(1) == null ? null : value.get(1).toString();
                    String latitude = value.get(2) == null ? null : value.get(2).toString();
                    a.setId(i);
                    //System.out.println(latitude);
                    //System.out.println(longitude);
                    a.setLatitude(Double.valueOf(latitude));
                    a.setLongitude(Double.valueOf(longitude));
                    res.add(a);
                    i=i+1;

                }
            }
        }
        return res;
    }

    @Override
    public List<Double> getOneValue(String sportTag, String key) {
        QueryResult results = influxDbUtils
                .query("SELECT "+ key +" FROM sportDetail " +
                        "where sportTag = '"+ sportTag +"'  order by time asc");

        QueryResult.Result oneResult = results.getResults().get(0);
        List<List<Object>> valueList = null;
        List<Double>res = new ArrayList<>();
        if (oneResult.getSeries() != null)
        {
            valueList = oneResult.getSeries().stream().map(QueryResult.Series::getValues)
                    .collect(Collectors.toList()).get(0);
            if (valueList != null && valueList.size() > 0) {
                for (List<Object> value : valueList) {

                    String result = value.get(1) == null ? null : value.get(1).toString();

                    res.add(Double.valueOf(result));
                }
            }
        }
        return res;
    }

    @Override
    public List<SpeedElevation> getSpeedElevation(String sportTag) {
        QueryResult results = influxDbUtils
                .query("SELECT altitude,speed FROM sportDetail " +
                        "where sportTag = '"+ sportTag +"'  order by time asc");

        QueryResult.Result oneResult = results.getResults().get(0);
        List<List<Object>> valueList = null;
        List<SpeedElevation>res = new ArrayList<>();
        if (oneResult.getSeries() != null)
        {
            valueList = oneResult.getSeries().stream().map(QueryResult.Series::getValues)
                    .collect(Collectors.toList()).get(0);
            if (valueList != null && valueList.size() > 0) {
                for (List<Object> value : valueList) {
                    SpeedElevation speedElevation =new SpeedElevation();
                    String time = value.get(0) == null ? null : value.get(0).toString();
                    String altitude = value.get(1) == null ? null : value.get(1).toString();
                    String speed = value.get(2) == null ? null : value.get(2).toString();
                    speedElevation.setElevation(Double.valueOf(altitude));
                    speedElevation.setSpeed(Double.valueOf(speed));
                    speedElevation.setTime(time);
                    res.add(speedElevation);
                }
            }
        }
        return res;
    }
}
