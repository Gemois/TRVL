package com.iee.trvlapp.ui.Costumers;


import androidx.lifecycle.ViewModel;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Packages;
import com.iee.trvlapp.roomEntities.Tours;

import java.util.List;

public class CostumersViewModel extends ViewModel {
    private Tours tourForAdapter;
    private Packages packageForAdapter;
    private List<Packages> packageList;
    private List<CityHotels> hotelsList;
    public CostumersViewModel() {
        packageList = MainActivity.appDatabase.packagesDao().getPackagesList();
    }


    public Packages getPackageById(int id) {
        packageForAdapter = MainActivity.appDatabase.packagesDao().getPackageById(id);
        return packageForAdapter;
    }

    public List<Packages> getAllPackages() {
        return packageList;
    }

    public Tours getTourById(int id) {
        tourForAdapter = MainActivity.appDatabase.toursDao().getTourById(id);
        return tourForAdapter;
    }

    public List<CityHotels> getHotelsList(int id) {
        hotelsList = MainActivity.appDatabase.cityHotelsDao().getCityHotelsByTid(id);
        return hotelsList;
    }


    public Offices getOfficeById(int id) {
       return MainActivity.appDatabase.officesDao().getOfficeById(id);
    }

}
