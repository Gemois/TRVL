package com.iee.trvlapp.roomEntities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface OfficesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void addOffice(Offices office);

    @Update(onConflict = OnConflictStrategy.ABORT)
    public void updateOffices(Offices office);

    @Delete()
    public void deleteOffices(Offices office);

    @Query("DELETE FROM offices_table where 1=1")
    public void deleteAllOffices();

    @Query("select * from offices_table")
    public LiveData<List<Offices>> getOffices();

    @Query("select * from offices_table")
    public List<Offices> getOfficesList();

    @Query("select * from offices_table  where Offices_id=:id")
    public Offices getOfficeById(int id);

    @Query("select * from offices_table order by Offices_name DESC")
    public LiveData<List<Offices>> getOfficesOrderedByNameDesc();

    @Query("select * from offices_table order by Offices_name ASC")
    public LiveData<List<Offices>> getOfficesOrderedByNameASC();

}
