package com.iee.trvlapp.ui.packages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

import java.util.List;

public class PackageViewModel extends ViewModel {

    private final LiveData<List<Package>> packageList;
    private final List<Tour> toursList;
    private final List<Office> officesList;

    public PackageViewModel() {
        packageList = MainActivity.appDatabase.packagesDao().getPackages();
        toursList = MainActivity.appDatabase.toursDao().getToursList();
        officesList = MainActivity.appDatabase.officesDao().getOfficesList();
    }

    public LiveData<List<Package>> getAllPackages() {
        return packageList;
    }

    public List<Tour> getAllTours() {
        return toursList;
    }

    public List<Office> getAllOffices() {
        return officesList;
    }

    public void deletePackage(Package packages) {
        MainActivity.appDatabase.packagesDao().deletePackages(packages);
    }

    public void updatePackage(Package packages) {
        MainActivity.appDatabase.packagesDao().updatePackages(packages);
    }

    public void deleteAll() {
        MainActivity.appDatabase.packagesDao().deleteAllPackages();
    }

}

