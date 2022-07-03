package com.iee.trvlapp.ui.Hotels;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Tours;

import java.util.List;

public class HotelsViewModel extends AndroidViewModel {

    private LiveData<List<CityHotels>> hotelsListStarsDESC;
    private List<Tours> toursList;

    public HotelsViewModel(Application application) {
        super(application);
        toursList = MainActivity.appDatabase.toursDao().getToursList();
        hotelsListStarsDESC = MainActivity.appDatabase.cityHotelsDao().getHotelsOrderedByStarsDesc();
    }

    // CityHotels Dao methods implementation for Hotels Fragment

    public List<Tours> getAllTours() {
        return toursList;
    }

    public void deleteAll() {
        MainActivity.appDatabase.cityHotelsDao().deleteAllCityHotels();
    }


    public void deleteHotel(CityHotels hotel) {
        MainActivity.appDatabase.cityHotelsDao().deleteCityHotels(hotel);
    }

    public void updateHotel(CityHotels hotels) {
        MainActivity.appDatabase.cityHotelsDao().updateCityHotel(hotels);
    }
    public CityHotels getHotelById(int id){
        return MainActivity.appDatabase.cityHotelsDao().getCityHotelById(id);
    }

    public LiveData<List<CityHotels>> getHotelsOrderByStarsDesc() {
        return hotelsListStarsDESC;
    }

public Tours getTourById(int id){
       return MainActivity.appDatabase.toursDao().getTourById(id);
}




}
