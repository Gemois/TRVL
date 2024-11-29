package com.iee.trvlapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iee.trvlapp.entities.Office;

import java.util.List;

@Dao
public interface OfficeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void addOffice(Office office);

    @Update(onConflict = OnConflictStrategy.ABORT)
    public void updateOffices(Office office);

    @Delete()
    public void deleteOffices(Office office);

    @Query("DELETE FROM offices_table where 1=1")
    public void deleteAllOffices();

    @Query("select * from offices_table")
    public LiveData<List<Office>> getOffices();

    @Query("select * from offices_table")
    public List<Office> getOfficesList();

    @Query("select * from offices_table  where Offices_id=:id")
    public Office getOfficeById(int id);

    @Query("select * from offices_table order by Offices_name DESC")
    public LiveData<List<Office>> getOfficesOrderedByNameDesc();

    @Query("select * from offices_table order by Offices_name ASC")
    public LiveData<List<Office>> getOfficesOrderedByNameASC();

}
