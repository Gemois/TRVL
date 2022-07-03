package com.iee.trvlapp.ui.Hotels;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentAddHotelsBinding;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Tours;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AddHotelsFragment extends Fragment {

    private FragmentAddHotelsBinding binding;

    Bitmap bitmap = null;
    final int SELECT_PHOTO = 1;
    Uri uri;

    AutoCompleteTextView autocompleteText;
    ArrayAdapter<String> adapterItems;
    List<Tours> toursList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HotelsViewModel hotelsViewModel =
                new ViewModelProvider(this).get(HotelsViewModel.class);

        binding = FragmentAddHotelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Calls function to handle CityHotel Insertion

        binding.addHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertOfficeData();
            }
        });

        // Add Action is Canceled and Navigates to Hotels Fragment

        binding.cancelUpdateHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_addHotelsFragment_to_nav_hotels);
            }
        });

        //Image Selection onClick

        binding.addbrowseImageLibraryHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(view);
            }
        });


        // Supports Dynamic autocomplete ListView for tourList on AddPackage Fragment

        int i = 0;

        toursList = hotelsViewModel.getAllTours();
        String[] items = new String[toursList.size()];
        for (Tours tour : toursList
        ) {
            items[i] = String.valueOf(tour.getTid()) + " " + tour.getCity();
            i++;
        }

        autocompleteText = binding.getRoot().findViewById(R.id.auto_complete_Htid);
        adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, items);

        autocompleteText.setAdapter(adapterItems);
        autocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });


        return root;
    }


    //Inserts  CityHotel
    public void insertOfficeData() {

        String name = binding.addHotelName.getText().toString();
        String address = binding.addHotelAddress.getText().toString();
        String stars = binding.addHotelStars.getText().toString();

        if (binding.addHotelName.length() != 0 && binding.addHotelAddress.length() != 0 && binding.addHotelStars.length() != 0 && binding.autoCompleteHtid.length() != 0) {
            CityHotels cityHotels = new CityHotels();
            cityHotels.setHotelName(name);
            cityHotels.setHotelAddress(address);
            cityHotels.setHotelStars(Integer.parseInt(stars));

            int tour_id = 0;
            String tour_idString = binding.autoCompleteHtid.getText().toString();
            if (!tour_idString.isEmpty()) {
                String tour_idCut = tour_idString.substring(0, tour_idString.indexOf(" "));
                tour_id = Integer.parseInt(tour_idCut);
            }

            cityHotels.setTid(tour_id);
            if (bitmap != null) {
                cityHotels.setImageHotel(DataConverter.convertIMage2ByteArray(bitmap));
            }
            MainActivity.appDatabase.cityHotelsDao().addCityHotel(cityHotels);

            Toast.makeText(getActivity(), "Hotel added !", Toast.LENGTH_LONG).show();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addHotelsFragment_to_nav_hotels);
        } else {
            Toast.makeText(getActivity(), "Fill all the fields", Toast.LENGTH_LONG).show();
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
            binding.landAddHOtelPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
        }
        if(uri!=null) {
            Toast.makeText(getActivity(), "Image selected !", Toast.LENGTH_SHORT).show();
        }    }

}
