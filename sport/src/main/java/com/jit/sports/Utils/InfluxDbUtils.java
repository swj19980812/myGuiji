package com.jit.sports.Utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.Data;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

/**
 * InfluxDB数据库连接操作类
 *
 */
@Data
public class InfluxDbUtils
{
    // 用户名
    private String username;
    // 密码
    private String password;
    // 连接地址
    private String openurl;
    // 数据库
    private String database;
    // 保留策略
    private String retentionPolicy;

    private static InfluxDB influxDB;

    public InfluxDbUtils(String username, String password, String openurl, String database, String retentionPolicy) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
        //保存策略，默认为“autogen”,但是有的版本默认是“default”，可以用show retention policies on databasename查看
        this.retentionPolicy = retentionPolicy == null || retentionPolicy.equals("") ? "default" : retentionPolicy;
        influxDbBuild();
    }

    /**
     * 测试连接是否正常
     */
    public boolean ping() {
        boolean isConnected = false;
        Pong pong;
        try
        {
            pong = influxDB.ping();
            if (pong != null)
            {
                isConnected = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return isConnected;
    }

    /**
     * 连接时序数据库 ，若不存在则创建
     *
     * @return
     */
    public InfluxDB influxDbBuild() {
        if (influxDB == null)
        {
            influxDB = InfluxDBFactory.connect(openurl, username, password);
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.NONE);
        System.out.println("连接influx");
        return influxDB;
    }

    /**
     * 创建自定义保留策略
     */
    public void createRetentionPolicy(String policyName, String duration,
                                      int replication, Boolean isDefault) {
        String sql
                = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s ", policyName,
                database, duration, replication);
        if (isDefault)
        {
            sql = sql + " DEFAULT";
        }
        this.query(sql);
    }

    /**
     * 创建默认的保留策略
     */
    public void createDefaultRetentionPolicy() {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "default", database, "30d", 1);
        this.query(command);
    }

    /**
     * 查询
     */
    public QueryResult query(String command) {
//        System.out.println(command);
        return influxDB.query(new Query(command, database));
    }

    /**
     * 插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
                       TimeUnit timeUnit)
    {
        Point.Builder pointBuilder = Point.measurement(measurement);
        pointBuilder.tag(tags);
        pointBuilder.fields(fields);
        if (0 != time)
        {
            pointBuilder.time(time, timeUnit);
        }
        influxDB.write(database, retentionPolicy, pointBuilder.build());
    }

    /**
     * 删除
     */
    public String deleteMeasurementData(String command)
    {
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }

    /**
     * 关闭数据库
     */
    public void close()
    {
        influxDB.close();
    }

}
