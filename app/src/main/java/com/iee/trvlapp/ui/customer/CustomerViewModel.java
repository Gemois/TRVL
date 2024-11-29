package com.iee.trvlapp.ui.customer;


import androidx.lifecycle.ViewModel;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.entities.CityHotel;
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

import java.util.List;

public class CustomerViewModel extends ViewModel {
    private final List<Package> packageList;

    public CustomerViewModel() {
        packageList = MainActivity.appDatabase.packagesDao().getPackagesList();
    }

    public Package getPackageById(int id) {
        return MainActivity.appDatabase.packagesDao().getPackageById(id);
    }

    public List<Package> getAllPackages() {
        return packageList;
    }

    public Tour getTourById(int id) {
        return MainActivity.appDatabase.toursDao().getTourById(id);
    }

    public List<CityHotel> getHotelsList(int id) {
        return MainActivity.appDatabase.cityHotelsDao().getCityHotelsByTid(id);
    }


    public Office getOfficeById(int id) {
        return MainActivity.appDatabase.officesDao().getOfficeById(id);
    }

}
