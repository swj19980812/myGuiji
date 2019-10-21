package com.jit.sports.entry;

public class SportInfo {

    private String sportTag;
    private String userName;
    private String startTime;
    private String overTime;
    private double totalDistance;
    private double totalUp;
    private double totalDown;
    private double averageSpeed;
    private double maxSpeed;
    private double maxAltitude;
    private String sportTitle;
    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSportTitle() {
        return sportTitle;
    }

    public void setSportTitle(String sportTitle) {
        this.sportTitle = sportTitle;
    }

    public double getMinAltitude() {
        return minAltitude;
    }

    public void setMinAltitude(double minAltitude) {
        this.minAltitude = minAltitude;
    }

    private double minAltitude;

    public double getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(double maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public String getSportTag() {
        return sportTag;
    }

    public void setSportTag(String sportTag) {
        this.sportTag = sportTag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalUp() {
        return totalUp;
    }

    public void setTotalUp(double totalUp) {
        this.totalUp = totalUp;
    }

    public double getTotalDown() {
        return totalDown;
    }

    public void setTotalDown(double totalDown) {
        this.totalDown = totalDown;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
