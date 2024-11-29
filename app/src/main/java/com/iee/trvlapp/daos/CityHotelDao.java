package com.iee.trvlapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iee.trvlapp.entities.CityHotel;

import java.util.List;

@Dao
public interface CityHotelDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void addCityHotel(CityHotel cityHotels);

    @Update(onConflict = OnConflictStrategy.ABORT)
    public void updateCityHotel(CityHotel cityHotels);

    @Delete()
    public void deleteCityHotels(CityHotel cityHotels);

    @Query("DELETE FROM cityhotels_table where 1=1")
    public void deleteAllCityHotels();

    @Query("select * from cityhotels_table")
    public LiveData<List<CityHotel>> getCityHotels();

    @Query("select * from cityhotels_table")
    public List<CityHotel> getCityHotelsList();

    @Query("select * from cityhotels_table where Hotels_tid=:id")
    public List<CityHotel> getCityHotelsByTid(int id);

    @Query("select * from cityhotels_table  where Hotels_id=:id")
    public CityHotel getCityHotelById(int id);

    @Query("select * from cityhotels_table order by Hotels_stars DESC")
    public LiveData<List<CityHotel>> getHotelsOrderedByStarsDesc();

    @Query("select * from cityhotels_table order by Hotels_name ASC")
    public LiveData<List<CityHotel>> getHotelsOrderedByNameASC();

}
