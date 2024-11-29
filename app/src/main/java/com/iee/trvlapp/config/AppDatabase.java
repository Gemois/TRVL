package com.iee.trvlapp.config;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.iee.trvlapp.converters.DataConverter;
import com.iee.trvlapp.daos.CityHotelDao;
import com.iee.trvlapp.daos.OfficeDao;
import com.iee.trvlapp.daos.PackageDao;
import com.iee.trvlapp.daos.TourDao;
import com.iee.trvlapp.entities.CityHotel;
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

@Database(entities = {Office.class, Tour.class, Package.class, CityHotel.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OfficeDao officesDao();

    public abstract TourDao toursDao();

    public abstract PackageDao packagesDao();

    public abstract CityHotelDao cityHotelsDao();

}
