package com.jit.sports.extra;

import org.springframework.lang.NonNull;

public class MyLatLngPoint implements Comparable<MyLatLngPoint>{
    public long id;
    public double longitude;
    public  double latitude;

    public MyLatLngPoint(long id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public MyLatLngPoint() {
    }
    @Override
    public int compareTo(@NonNull MyLatLngPoint o)
    {
        if(this.id < o.id)
        {
            return -1;
        }
        else if (this.id > o.id)
        {
            return 1;
        }
        return 0;
    }
    public long getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }



}
