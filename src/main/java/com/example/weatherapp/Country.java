package com.example.weatherapp;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private String country;
    private List<City> cities;
    public Country(String country, List<City> cities){
        this.country = country;
        this.cities = cities;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return this.country;
    }
}
