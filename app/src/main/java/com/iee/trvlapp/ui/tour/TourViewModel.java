package com.iee.trvlapp.ui.tour;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.entities.Tour;

import java.util.List;

public class TourViewModel extends ViewModel {

    private final LiveData<List<Tour>> toursList;

    public TourViewModel() {
        toursList = MainActivity.appDatabase.toursDao().getTours();
    }

    public void deleteAll() {
        MainActivity.appDatabase.toursDao().deleteAllTours();
    }

    public LiveData<List<Tour>> getAllTours() {
        return toursList;
    }

    public void deleteTour(Tour tour) {
        MainActivity.appDatabase.toursDao().deleteTours(tour);
    }

    public void updateTour(Tour tour) {
        MainActivity.appDatabase.toursDao().updateTours(tour);
    }

    public Tour getTourById(int id) {
        return MainActivity.appDatabase.toursDao().getTourById(id);
    }

}