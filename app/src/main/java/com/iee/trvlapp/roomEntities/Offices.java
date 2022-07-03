package com.iee.trvlapp.roomEntities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.ParcelField;
import androidx.versionedparcelable.VersionedParcelize;
@VersionedParcelize
@Entity(tableName = "offices_table")
public class Offices {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Offices_id")
    @NonNull
    private int ofid;
    @ColumnInfo(name = "Offices_name")
    private String name;
    @ColumnInfo(name = "Offices_address")
    private String address;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public Offices() {
    }

    @Ignore
    public Offices(int ofid, String name, String address) {
        this.ofid = ofid;
        this.name = name;
        this.address = address;
    }

    @Ignore
    public Offices(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getOfid() {
        return ofid;
    }

    public void setOfid(int ofid) {
        this.ofid = ofid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
