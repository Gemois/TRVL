package com.iee.trvlapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iee.trvlapp.entities.Tour;

import java.util.List;

@Dao
public interface TourDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void addTour(Tour tour);

    @Update(onConflict = OnConflictStrategy.ABORT)
    public void updateTours(Tour tour);

    @Delete
    public void deleteTours(Tour tour);

    @Query("DELETE FROM Tours_table where 1=1")
    public void deleteAllTours();

    @Query("select * from Tours_table")
    public LiveData<List<Tour>> getTours();

    @Query("select * from Tours_table")
    public List<Tour> getToursList();

    @Query("select * from Tours_table  where Tours_id=:id")
    public Tour getTourById(int id);

    @Query("select * from Tours_table order by Tours_city DESC")
    public LiveData<List<Tour>> getToursOrderedByNameDesc();

    @Query("select * from Tours_table order by Tours_city ASC")
    public LiveData<List<Tour>> getToursOrderedByNameASC();

}
