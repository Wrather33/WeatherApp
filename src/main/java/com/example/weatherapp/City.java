package com.example.weatherapp;

import javafx.scene.image.ImageView;

public class City {
    private String city;
    private String latitude;
    private String longitude;
    public City(String city, String latitude, String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return this.city;
    }
}
