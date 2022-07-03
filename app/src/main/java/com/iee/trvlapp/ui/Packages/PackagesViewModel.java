package com.iee.trvlapp.ui.Packages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Packages;
import com.iee.trvlapp.roomEntities.Tours;

import java.util.List;

public class PackagesViewModel extends ViewModel {


    private LiveData<List<Packages>> packageList;

    private List<Tours> toursList;
    private List<Offices> officesList;

    public PackagesViewModel() {
        packageList = MainActivity.appDatabase.packagesDao().getPackages();
        toursList = MainActivity.appDatabase.toursDao().getToursList();
        officesList = MainActivity.appDatabase.officesDao().getOfficesList();
    }

    //Packages Dao methods implementation for Packages Fragment

    public LiveData<List<Packages>> getAllPackages() {
        return packageList;
    }

    public List<Tours> getAllTours() {
        return toursList;
    }

    public List<Offices> getAllOffices() {
        return officesList;
    }

    public void deletePackage(Packages packages) {
        MainActivity.appDatabase.packagesDao().deletePackages(packages);
    }

    public void updatePackage(Packages packages) {
        MainActivity.appDatabase.packagesDao().updatePackages(packages);
    }

    public void deleteAll() {
        MainActivity.appDatabase.packagesDao().deleteAllPackages();
    }

}

