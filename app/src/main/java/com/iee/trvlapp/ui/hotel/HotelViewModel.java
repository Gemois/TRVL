package com.iee.trvlapp.ui.hotel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.entities.CityHotel;
import com.iee.trvlapp.entities.Tour;

import java.util.List;

public class HotelViewModel extends AndroidViewModel {

    private final LiveData<List<CityHotel>> hotelsListStarsDESC;
    private final List<Tour> toursList;

    public HotelViewModel(Application application) {
        super(application);
        toursList = MainActivity.appDatabase.toursDao().getToursList();
        hotelsListStarsDESC = MainActivity.appDatabase.cityHotelsDao().getHotelsOrderedByStarsDesc();
    }


    public List<Tour> getAllTours() {
        return toursList;
    }

    public void deleteAll() {
        MainActivity.appDatabase.cityHotelsDao().deleteAllCityHotels();
    }


    public void deleteHotel(CityHotel hotel) {
        MainActivity.appDatabase.cityHotelsDao().deleteCityHotels(hotel);
    }

    public void updateHotel(CityHotel hotels) {
        MainActivity.appDatabase.cityHotelsDao().updateCityHotel(hotels);
    }

    public CityHotel getHotelById(int id) {
        return MainActivity.appDatabase.cityHotelsDao().getCityHotelById(id);
    }

    public LiveData<List<CityHotel>> getHotelsOrderByStarsDesc() {
        return hotelsListStarsDESC;
    }

    public Tour getTourById(int id) {
        return MainActivity.appDatabase.toursDao().getTourById(id);
    }


}
