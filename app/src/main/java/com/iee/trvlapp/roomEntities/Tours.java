package com.iee.trvlapp.roomEntities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tours_table")
public class Tours {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Tours_id")
    @NonNull
    private int tid;
    @ColumnInfo(name = "Tours_city")
    private String city;
    @ColumnInfo(name = "Tours_country")
    private String country;
    @ColumnInfo(name = "Tours_duration")
    private int duration;
    @ColumnInfo(name = "Tours_type")
    private String type;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageTour;

    public Tours() {
    }

    @Ignore
    public Tours(String city, String country, int duration, String type) {
        this.city = city;
        this.country = country;
        this.duration = duration;
        this.type = type;
    }

    @Ignore
    public Tours(int tid, String city, String country, int duration, String type) {
        this.tid = tid;
        this.city = city;
        this.country = country;
        this.duration = duration;
        this.type = type;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageTour() {
        return imageTour;
    }

    public void setImageTour(byte[] imageTour) {
        this.imageTour = imageTour;
    }
}
