package com.iee.trvlapp.ui.Tours;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.roomEntities.Tours;
import java.util.List;

public class ToursViewModel extends ViewModel {

    private LiveData<List<Tours>> toursList;

    public ToursViewModel() {
        toursList = MainActivity.appDatabase.toursDao().getTours();
    }


    //Tours Dao methods implementation for Tours Fragment

    public void deleteAll(){
        MainActivity.appDatabase.toursDao().deleteAllTours();
    }

    public LiveData<List<Tours>> getAllTours() {
        return toursList;
    }

    public void deleteTour(Tours tour) {
        MainActivity.appDatabase.toursDao().deleteTours(tour);
    }

    public void updateTour(Tours tour) {
        MainActivity.appDatabase.toursDao().updateTours(tour);
    }

    public Tours getTourById(int id){
        return MainActivity.appDatabase.toursDao().getTourById(id);
    }

}