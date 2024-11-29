package com.iee.trvlapp.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Packages_table",
        foreignKeys = {@ForeignKey(entity = Office.class,
                parentColumns = "Offices_id",
                childColumns = "Packages_ofid",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Tour.class,
                        parentColumns = "Tours_id",
                        childColumns = "Packages_tid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)})

public class Package {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Packages_id")
    @NonNull
    private int pid;
    @ColumnInfo(name = "Packages_ofid", index = true)
    @NonNull
    private int ofId;
    @ColumnInfo(name = "Packages_tid", index = true)
    @NonNull
    private int tid;
    @ColumnInfo(name = "Packages_departureTime")
    private String departureTime;
    @ColumnInfo(name = "Packages_cost")
    private Double cost;

    public Package() {}

    @Ignore
    public Package(int ofId, int tid, String  departureTime, Double cost) {
        this.ofId = ofId;
        this.tid = tid;
        this.departureTime = departureTime;
        this.cost = cost;
    }

    @Ignore
    public Package(int pid, int ofId, int tid, String  departureTime, Double cost) {
        this.pid = pid;
        this.ofId = ofId;
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

    public int getOfId() {
        return ofId;
    }
    public void setOfId(int ofId) {
        this.ofId = ofId;
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
