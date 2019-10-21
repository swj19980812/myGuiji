package com.jit.sports.entry;

public class SportDetailInfo {
    private String time;
    private double longitude;
    private double latitude;
    private double altitude;
    private double speed;
    private double direction_x;
    private double direction_y;
    private double direction_z;
    private double accelerated_x;
    private double accelerated_y;
    private double accelerated_z;
    private int steps;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirection_x() {
        return direction_x;
    }

    public void setDirection_x(double direction_x) {
        this.direction_x = direction_x;
    }

    public double getDirection_y() {
        return direction_y;
    }

    public void setDirection_y(double direction_y) {
        this.direction_y = direction_y;
    }

    public double getDirection_z() {
        return direction_z;
    }

    public void setDirection_z(double direction_z) {
        this.direction_z = direction_z;
    }

    public double getAccelerated_x() {
        return accelerated_x;
    }

    public void setAccelerated_x(double accelerated_x) {
        this.accelerated_x = accelerated_x;
    }

    public double getAccelerated_y() {
        return accelerated_y;
    }

    public void setAccelerated_y(double accelerated_y) {
        this.accelerated_y = accelerated_y;
    }

    public double getAccelerated_z() {
        return accelerated_z;
    }

    public void setAccelerated_z(double accelerated_z) {
        this.accelerated_z = accelerated_z;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
