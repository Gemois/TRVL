package com.iee.trvlapp.ui.office;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.entities.Office;

import java.util.List;

public class OfficeViewModel extends AndroidViewModel {

    private LiveData<List<Office>> officesList;

    public OfficeViewModel(Application application) {
        super(application);
        officesList = MainActivity.appDatabase.officesDao().getOffices();
    }

    public LiveData<List<Office>> getAllOffices() {
        return officesList;
    }

    public void deleteOffice(Office office) {
        MainActivity.appDatabase.officesDao().deleteOffices(office);
    }

    public Office getOfficeById(int id) {
        return MainActivity.appDatabase.officesDao().getOfficeById(id);
    }

    public void deleteAll() {
        MainActivity.appDatabase.officesDao().deleteAllOffices();
    }

    public void updateOffice(Office office) {
        MainActivity.appDatabase.officesDao().updateOffices(office);
    }

}