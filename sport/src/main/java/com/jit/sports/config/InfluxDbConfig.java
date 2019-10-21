package com.jit.sports.config;
import com.jit.sports.Utils.InfluxDbUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "influxdb")
public class InfluxDbConfig {
    private String url;
    private String username;
    private String password;
    private String database;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Bean
    public InfluxDbUtils influxDbUtils() {
        return new InfluxDbUtils(username, password, url, database, "");
    }
}
