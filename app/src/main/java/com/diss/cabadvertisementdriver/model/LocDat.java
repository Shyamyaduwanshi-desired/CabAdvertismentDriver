package com.diss.cabadvertisementdriver.model;

public class LocDat  {
    double lat, lng;
    String userId;

    public LocDat(String  userid,double lat, double lng) {
        this.userId = userid;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
