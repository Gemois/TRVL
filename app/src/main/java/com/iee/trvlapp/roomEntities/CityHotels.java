package com.iee.trvlapp.roomEntities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CityHotels_table",
        foreignKeys = {
                @ForeignKey(entity = Tours.class,
                        parentColumns = "Tours_id",
                        childColumns = "Hotels_tid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)})


public class CityHotels {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Hotels_id")
    @NonNull
    private int hid;
    @ColumnInfo(name = "Hotels_name")
    private String hotelName;
    @ColumnInfo(name = "Hotels_address")
    private String hotelAddress;
    @ColumnInfo(name = "Hotels_stars")
    private int hotelStars;
    @ColumnInfo(name = "Hotels_tid", index = true)
    private int tid;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageHotel;


    public CityHotels() {
    }

    @Ignore
    public CityHotels(String hotelName, String hotelAddress, int hotelStars, int tid) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelStars = hotelStars;
        this.tid = tid;
    }

    @Ignore
    public CityHotels(int hid, String hotelName, String hotelAddress, int hotelStars, int tid) {
        this.hid = hid;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelStars = hotelStars;
        this.tid = tid;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public int getHotelStars() {
        return hotelStars;
    }

    public void setHotelStars(int hotelStars) {
        this.hotelStars = hotelStars;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public byte[] getImageHotel() {
        return imageHotel;
    }

    public void setImageHotel(byte[] imageHotel) {
        this.imageHotel = imageHotel;
    }
}
