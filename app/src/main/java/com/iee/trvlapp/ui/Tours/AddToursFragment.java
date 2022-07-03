package com.iee.trvlapp.ui.Tours;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentAddToursBinding;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Tours;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AddToursFragment extends Fragment {

    private FragmentAddToursBinding binding;
    Bitmap bitmap = null;
    final int SELECT_PHOTO = 1;
    Uri uri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ToursViewModel toursViewModel =
                new ViewModelProvider(this).get(ToursViewModel.class);

        binding = FragmentAddToursBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Calls function to handle Tour Insertion

        binding.toursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToursData();
            }
        });

        // Add Action is Canceled and Navigates to Tours Fragment

        binding.cancelUpdateToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_addToursFragment_to_nav_tours);
            }
        });

        //Image Selection onClick

        binding.addbrowseImageLibraryTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(view);
            }
        });


        return root;
    }


    ////Inserts  Tour

    public void insertToursData() {

        String tour_city = binding.TourCity.getText().toString();
        String tour_country = binding.TourCountry.getText().toString();
        String tour_duration = binding.tourDuration.getText().toString();
        String tour_type = binding.tourType.getText().toString();

        if (binding.TourCity.length() != 0 && binding.TourCountry.length() != 0 && binding.tourDuration.length() != 0 && binding.tourType.length() != 0) {

            Tours tour = new Tours();
            tour.setCity(tour_city);
            tour.setCountry(tour_country);
            tour.setDuration(Integer.parseInt(tour_duration));
            tour.setType(tour_type);
            if (bitmap != null) {
                tour.setImageTour(DataConverter.convertIMage2ByteArray(bitmap));
            }
            MainActivity.appDatabase.toursDao().addTour(tour);

            Toast.makeText(getActivity(), "Tour added !", Toast.LENGTH_LONG).show();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addToursFragment_to_nav_tours);
        } else {
            Toast.makeText(getActivity(), "Fill all the fields !", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Makes Intent to handle Image selection

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    //Opens image selector and gets the image uri

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PHOTO && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            binding.landAddTourImagePreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
        }

if(uri!=null) {
    Toast.makeText(getActivity(), "Image selected !", Toast.LENGTH_SHORT).show();
}
    }

}
