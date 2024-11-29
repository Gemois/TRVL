package com.iee.trvlapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iee.trvlapp.entities.Package;

import java.util.List;

@Dao
public interface PackageDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void addPackage(Package Package);

    @Update(onConflict = OnConflictStrategy.ABORT)
    public void updatePackages(Package Package);

    @Delete()
    public void deletePackages(Package Package);

    @Query("DELETE FROM Packages_table where 1=1")
    public void deleteAllPackages();

    @Query("select * from Packages_table")
    public LiveData<List<Package>> getPackages();

    @Query("select * from Packages_table")
    public List<Package> getPackagesList();

    @Query("select * from Packages_table where Packages_id=:id")
    public Package getPackageById(int id);

    @Query("select * from Packages_table order by Packages_cost DESC")
    public LiveData<List<Package>> getPackagesOrderedByNameDesc();

    @Query("select * from Packages_table order by Packages_cost ASC")
    public LiveData<List<Package>> getPackagesOrderedByNameASC();

}
