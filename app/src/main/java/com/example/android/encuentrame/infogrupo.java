package com.example.android.encuentrame;

/**
 * Created by W7 on 5/17/2017.
 */

public class infogrupo {

    public String id;
    public Double lng;
    public int lat;

    public infogrupo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public infogrupo(String id, Integer lat, Double lng) {
        this.id = id;
        this.lat = lat;
        this.lng=lng;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
