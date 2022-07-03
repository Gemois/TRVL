package com.iee.trvlapp.ObjectEntities;

public class CityCoordinates {
    private String CityNAme;
    private double longitude;
    private double latitude;

    public CityCoordinates() {
    }

    public CityCoordinates(String cityNAme, double longitude, double latitude) {
        CityNAme = cityNAme;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCityNAme() {
        return CityNAme;
    }

    public void setCityNAme(String cityNAme) {
        CityNAme = cityNAme;
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

    @Override
    public String toString() {
        return "CityCoordinates{" +
                "CityNAme='" + CityNAme + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
