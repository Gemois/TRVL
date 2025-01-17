package com.iee.trvlapp.ui.tour;

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

import com.iee.trvlapp.R;
import com.iee.trvlapp.converters.DataConverter;
import com.iee.trvlapp.databinding.FragmentUpdateToursBinding;
import com.iee.trvlapp.entities.Tour;

import java.io.FileNotFoundException;
import java.io.IOException;


public class UpdateTourFragment extends Fragment {

    private FragmentUpdateToursBinding binding;
    private Bitmap bitmap = null;
    private final int SELECT_PHOTO = 1;
    private Uri uri;
    private boolean flag = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TourViewModel toursViewModel = new ViewModelProvider(this).get(TourViewModel.class);

        binding = FragmentUpdateToursBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Retrieves Data passed From Tour Fragment
        int id = UpdateTourFragmentArgs.fromBundle(getArguments()).getTourId();
        String city = UpdateTourFragmentArgs.fromBundle(getArguments()).getTourCity();
        String country = UpdateTourFragmentArgs.fromBundle(getArguments()).getTourCountry();
        int duration = UpdateTourFragmentArgs.fromBundle(getArguments()).getTourDuration();
        String type = UpdateTourFragmentArgs.fromBundle(getArguments()).getTourType();
        byte[] image = toursViewModel.getTourById(id).getImageTour();

        binding.updateTourCity.setText(city);
        binding.updateTourCountry.setText(country);
        binding.updateTourDuration.setText(String.valueOf(duration));
        binding.updateTourType.setText(type);

        //  Updates Chosen Tour and Navigates to Tour Fragment
        binding.updateToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = binding.updateTourCity.getText().toString();
                String country = binding.updateTourCountry.getText().toString();
                String duration = binding.updateTourDuration.getText().toString();
                String type = binding.updateTourType.getText().toString();


                if (binding.updateTourCity.length() != 0 && binding.updateTourCountry.length() != 0 && binding.updateTourDuration.length() != 0 && binding.updateTourType.length() != 0) {
                    Tour tour = new Tour();
                    tour.setTid(id);
                    tour.setCity(city);
                    tour.setCountry(country);
                    tour.setDuration(Integer.parseInt(duration));
                    tour.setType(type);
                    if (flag) {
                        tour.setImageTour(DataConverter.convertIMage2ByteArray(bitmap));
                    } else {
                        tour.setImageTour(image);
                    }
                    toursViewModel.updateTour(tour);
                    Navigation.findNavController(view).navigate(R.id.action_updateToursFragment_to_nav_tours);
                    Toast.makeText(getActivity(), "Tour updated !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Fill all the fields !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Update Action is Canceled and Navigates to Tour Fragment
        binding.cancelToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_updateToursFragment_to_nav_tours);
            }
        });

        //Image Selection onClick
        binding.updatebrowseImageLibraryTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(view);
            }
        });


        return root;
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
            try {
                binding.updateTourPreview.setImageBitmap(bitmap);
            } catch (NullPointerException e) {
            }
        }
        if (uri != null) {
            flag = true;
            Toast.makeText(getActivity(), "Image selected !", Toast.LENGTH_SHORT).show();
        }
    }

}
