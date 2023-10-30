package com.example.weatherapp;

public class CurrentWeather {
    private String icon;
    private String weather;
    private double temp;
    private double feels;
    private double wind;
    private double gust;
    private int humidity;
    private int cloud;
    private double pressure;
    private int windDegree;
    private String direction;
    private int isDay;
    private double precipitation;
    private double ftemp;
    private double flike;
    public CurrentWeather(String icon, String weather, double temp, double feels, double wind, double gust, int humidity, int cloud, double pressure, int windDegree, String direction, int isDay, double precipitation, double ftemp, double flike) {
        this.icon = icon;
        this.weather = weather;
        this.temp = temp;
        this.feels = feels;
        this.wind = wind;
        this.gust = gust;
        this.humidity = humidity;
        this.cloud = cloud;
        this.pressure = pressure;
        this.windDegree = windDegree;
        this.direction = direction;
        this.isDay = isDay;
        this.precipitation = precipitation;
        this.ftemp = ftemp;
        this.flike = flike;
    }

    public void setFlike(double flike) {
        this.flike = flike;
    }

    public double getFlike() {
        return flike;
    }

    public void setFtemp(double ftemp) {
        this.ftemp = ftemp;
    }

    public double getFtemp() {
        return ftemp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels() {
        return feels;
    }

    public void setFeels(double feels) {
        this.feels = feels;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getIsDay() {
        return isDay;
    }

    public void setIsDay(int isDay) {
        this.isDay = isDay;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }
}
