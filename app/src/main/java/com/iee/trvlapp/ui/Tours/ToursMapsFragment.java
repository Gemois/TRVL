package com.iee.trvlapp.ui.Tours;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iee.trvlapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iee.trvlapp.ObjectEntities.CityCoordinates;


public class ToursMapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            CityCoordinates array[] = initCityCoordinates();


            String id = ToursMapsFragmentArgs.fromBundle(getArguments()).getCityName();

            CityCoordinates temp = new CityCoordinates();
            for (int i = 0; i < array.length; i++) {

                if (array[i].getCityNAme().equals(id) && array[i] != null) {
                    temp = array[i];
                }

            }

            LatLng latLng = new LatLng(temp.getLatitude(), temp.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker in "));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);


        return view;
    }


    //prestored City location Coordinates

    public CityCoordinates[] initCityCoordinates() {
        CityCoordinates London = new CityCoordinates("London", -0.117083, 51.513220);
        CityCoordinates Paris = new CityCoordinates("Paris", 2.342571, 48.867529);
        CityCoordinates Barcelona = new CityCoordinates("Barcelona", 2.151398, 41.391985);
        CityCoordinates Berlin = new CityCoordinates("Berlin", 13.387711, 52.514941);
        CityCoordinates Rome = new CityCoordinates("Rome", 12.489288, 41.884438);

        CityCoordinates NewYork = new CityCoordinates("New York", -74.079953, 40.709643);
        CityCoordinates Tokyo = new CityCoordinates("Tokyo", 139.769599, 35.679985);
        CityCoordinates Vienna = new CityCoordinates("Vienna", 16.363270, 48.201609);

        CityCoordinates[] array = new CityCoordinates[8];
        array[0] = London;
        array[1] = Barcelona;
        array[2] = Paris;
        array[3] = Rome;
        array[4] = NewYork;
        array[5] = Berlin;
        array[6] = Tokyo;
        array[7] = Vienna;
        return array;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}