package com.iee.trvlapp.roomEntities;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Offices.class, Tours.class, Packages.class, CityHotels.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OfficesDao officesDao();

    public abstract ToursDao toursDao();

    public abstract PackagesDao packagesDao();

    public abstract CityHotelsDao cityHotelsDao();

}
