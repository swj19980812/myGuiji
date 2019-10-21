package com.jit.sports.mqtt;

import com.jit.sports.config.ApplicationContextProvider;
import com.jit.sports.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;

public class MQTTClient {
    private static MqttClient client;
    MqttService mqttService = ApplicationContextProvider.getBean(MqttService.class);

    public static MqttClient getClient() {
        return client;
    }

    public static void setClient(MqttClient client) {
        MQTTClient.client = client;
    }

    public void connect(String host, String clientID, String username, String password, int timeout, int keepalive){
        MqttClient client;
        try {
            client = new MqttClient(host, clientID, new MemoryPersistence());
            MQTTClient.setClient(client);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepalive);
            options.setAutomaticReconnect(true);
            //设置回调函数
            System.out.println(mqttService);
            client.setCallback(mqttService);
            //连接
            client.connect(options);
            // 订阅消息
            client.subscribe("sports/sportInfo/#", 0);
            client.subscribe("Will", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic,String pushMessage){
        try {
            client.publish(topic, new MqttMessage(pushMessage.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}