package com.iee.trvlapp.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.VersionedParcelize;

@VersionedParcelize
@Entity(tableName = "offices_table")
public class Office {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Offices_id")
    @NonNull
    private int ofId;
    @ColumnInfo(name = "Offices_name")
    private String name;
    @ColumnInfo(name = "Offices_address")
    private String address;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public Office() {
    }

    @Ignore
    public Office(int ofId, String name, String address) {
        this.ofId = ofId;
        this.name = name;
        this.address = address;
    }

    @Ignore
    public Office(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getOfId() {
        return ofId;
    }
    public void setOfId(int ofID) {
        this.ofId = ofID;
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
