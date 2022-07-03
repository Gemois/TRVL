package com.iee.trvlapp.roomEntities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Packages_table",
        foreignKeys = {@ForeignKey(entity = Offices.class,
                parentColumns = "Offices_id",
                childColumns = "Packages_ofid",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Tours.class,
                        parentColumns = "Tours_id",
                        childColumns = "Packages_tid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)})


public class Packages {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Packages_id")
    @NonNull
    private int pid;
    @ColumnInfo(name = "Packages_ofid", index = true)
    @NonNull
    private int ofid;
    @ColumnInfo(name = "Packages_tid", index = true)
    @NonNull
    private int tid;
    @ColumnInfo(name = "Packages_departureTime")
    private String departureTime;
    @ColumnInfo(name = "Packages_cost")
    private Double cost;


    public Packages() {
    }

    @Ignore
    public Packages(int ofid, int tid, String  departureTime, Double cost) {
        this.ofid = ofid;
        this.tid = tid;
        this.departureTime = departureTime;
        this.cost = cost;
    }

    @Ignore
    public Packages(int pid, int ofid, int tid, String  departureTime, Double cost) {
        this.pid = pid;
        this.ofid = ofid;
        this.tid = tid;
        this.departureTime = departureTime;
        this.cost = cost;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getOfid() {
        return ofid;
    }

    public void setOfid(int ofid) {
        this.ofid = ofid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String  getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(String  departureTime) {
        this.departureTime = departureTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
