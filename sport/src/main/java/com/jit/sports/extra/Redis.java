package com.jit.sports.extra;

import com.alibaba.fastjson.JSONObject;
import com.jit.sports.Utils.PropertiesUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class Redis {


    public static Jedis jedis = new Jedis("47.102.152.12", 6379);

    public Redis (String sportTag,double longitude,double latitude,double altitude,int steps)
    {

        Map<String, String> laterRedis = new HashMap<>();
        laterRedis.put("longitude", "0");
        laterRedis.put("latitude", "0");
        laterRedis.put("altitude", "0");
        laterRedis.put("currentMileage", "0");
        laterRedis.put("currentTime", "0");
        laterRedis.put("currentUp", "0");
        laterRedis.put("currentDown", "0");
        laterRedis.put("startSteps","0");
        laterRedis.put("maxSpeed","0");
        laterRedis.put("maxAltitude","0");
        laterRedis.put("minAltitude","10000");
        // System.out.println("ddddddddddddddddddddddddddddddd");

        jedis.hmset(sportTag, laterRedis);

    }

    public static JSONObject getData(JSONObject obj) {
        // String JedisHost = PropertiesUtil.getProperty("redis.host");
        //    int port = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
        // Jedis jedis = new Jedis("47.102.152.12", 6379);
        JSONObject nowMessage = new JSONObject();

        String userTag = obj.getString("sportTag");
        double longitude = obj.getDoubleValue("longitude");
        double latitude = obj.getDoubleValue("latitude");
        double altitude = obj.getDoubleValue("altitude");
        Map<String, String> laterRedis = new HashMap<>();

        if(jedis.exists(obj.getString("sportTag"))) {
            // System.out.println(userTag);
            laterRedis = jedis.hgetAll(userTag);
            // System.out.println(laterRedis.get("startSteps"));
            if (laterRedis.get("startSteps").equals("0") == true )
            {
                laterRedis.put("longitude", String.valueOf(longitude));
                laterRedis.put("latitude", String.valueOf(latitude));
                laterRedis.put("altitude", String.valueOf(altitude));
                laterRedis.put("startSteps",String.valueOf(obj.getInteger("steps")));
                // System.out.println("hahahahhahahhahhahahhahahhahahahahahahahhahahaahhahahahahahah");
            }
            //计算返回信息
            Double distance = Double.valueOf(laterRedis.get("currentMileage")) + Sparse.getDistance(Double.valueOf(laterRedis.get("latitude")), Double.valueOf(laterRedis.get("longitude")), latitude, longitude);
            long time = Long.valueOf(laterRedis.get("currentTime")) + 1;
            double speed = distance / time;
            double xspeed = 0;
            double xsteps = 0;
            double currentDown = Double.valueOf(laterRedis.get("currentDown"));
            double currentUp = Double.valueOf(laterRedis.get("currentUp"));
            if ( Double.valueOf(laterRedis.get("altitude")) > altitude) {
                currentDown = Double.valueOf(laterRedis.get("currentDown"))+ Double.valueOf(laterRedis.get("altitude"))-altitude;
            } else {
                currentUp = Double.valueOf(laterRedis.get("currentUp")) + altitude-Double.valueOf(laterRedis.get("altitude"));
            }

            //跟新redis
            laterRedis.put("longitude", String.valueOf(longitude));
            laterRedis.put("latitude", String.valueOf(latitude));
            laterRedis.put("altitude", String.valueOf(altitude));
            laterRedis.put("currentMileage", String.valueOf(distance));
            laterRedis.put("currentTime", String.valueOf(time));
            laterRedis.put("currentUp", String.valueOf(currentUp));
            laterRedis.put("currentDown", String.valueOf(currentDown));

            laterRedis.put("maxSpeed",String.valueOf(Math.max(Double.valueOf(laterRedis.get("maxSpeed")),obj.getDoubleValue("speed"))));
            laterRedis.put("maxAltitude",String.valueOf(Math.max(Double.valueOf(laterRedis.get("maxAltitude")),altitude)));
            laterRedis.put("minAltitude",String.valueOf(Math.min(Double.valueOf(laterRedis.get("minAltitude")),altitude)));
            jedis.hmset(obj.getString("sportTag"), laterRedis);
            //给返回赋值信息

            nowMessage.put("currentMileage", Math.round(distance * 100.0) / 100.0);
            nowMessage.put("currentSpeed", Math.round((obj.getDoubleValue("speed")) * 100.0) / 100.0);
            nowMessage.put("averageSpeed", Math.round(speed * 100.0) / 100.0);
            nowMessage.put("xSpeed", Math.round(xspeed * 100.0) / 100.0);
            nowMessage.put("currentUp", Math.round(currentUp * 100.0) / 100.0);
            nowMessage.put("currentDown", Math.round(currentDown * 100.0) / 100.0);
            nowMessage.put("currentSteps", obj.getInteger("steps") - Long.valueOf(laterRedis.get("startSteps")));
            nowMessage.put("xSteps", xsteps);

            //System.out.println("2222222222222222222222222222222222222");
            // System.out.println(nowMessage.toString());
            // System.out.println("3333333333333333333333333333333333333");


        }

        return nowMessage;
    }



}
