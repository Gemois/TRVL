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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentUpdateHotelsBinding;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Tours;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class UpdateHotelsFragment extends Fragment {

    private FragmentUpdateHotelsBinding binding;

    AutoCompleteTextView autocompleteText;
    ArrayAdapter<String> adapterItems;
    List<Tours> toursList;

    Bitmap bitmap = null;
    final int SELECT_PHOTO = 1;
    Uri uri;
    boolean flag = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HotelsViewModel hotelsViewModel =
                new ViewModelProvider(this).get(HotelsViewModel.class);

        binding = FragmentUpdateHotelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Retrieves Data passed From CityHotels Fragment

        int id = UpdateHotelsFragmentArgs.fromBundle(getArguments()).getHotelId();
        String name = UpdateHotelsFragmentArgs.fromBundle(getArguments()).getHotelName();
        String address = UpdateHotelsFragmentArgs.fromBundle(getArguments()).getHotelAddress();
        int stars = UpdateHotelsFragmentArgs.fromBundle(getArguments()).getHotelStars();
        int tid = UpdateHotelsFragmentArgs.fromBundle(getArguments()).getHotelTid();
        byte [] image=hotelsViewModel.getHotelById(id).getImageHotel();

        binding.updateHotelName.setText(name);
        binding.updateHotelAddress.setText(address);
        binding.updateHotelStars.setText(String.valueOf(stars));


        //  //  Updates Chosen CityHotel and Navigates to CityHotel Fragment

        binding.updateHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.updateHotelName.getText().toString();
                String address = binding.updateHotelAddress.getText().toString();
                String stars = binding.updateHotelStars.getText().toString();

                if (binding.updateHotelName.length() != 0 && binding.updateHotelAddress.length() != 0 && binding.updateHotelStars.length() != 0) {

                    CityHotels cityHotels = new CityHotels();
                    cityHotels.setHid(id);
                    cityHotels.setHotelName(name);
                    cityHotels.setHotelAddress(address);
                    cityHotels.setHotelStars(Integer.parseInt(stars));

                    String tour_idString = binding.updateAutoCompleteHtid.getText().toString();
                    if (!tour_idString.isEmpty()) {
                        String tour_idCut = tour_idString.substring(0, tour_idString.indexOf(" "));
                        int tour_id = Integer.parseInt(tour_idCut);
                        cityHotels.setTid(tour_id);
                    } else {
                        cityHotels.setTid(tid);
                    }

                    if (flag) {
                        cityHotels.setImageHotel(DataConverter.convertIMage2ByteArray(bitmap));
                    } else {
                        cityHotels.setImageHotel(image);
                    }
                    hotelsViewModel.updateHotel(cityHotels);
                    Toast.makeText(getActivity(), "Hotel updated !", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_updateHotelsFragment_to_nav_hotels);
                } else {
                    Toast.makeText(getActivity(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Update Action is Canceled and Navigates to CityHotels Fragment

        binding.cancelUpdateHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_updateHotelsFragment_to_nav_hotels);
            }
        });


        int i = 0;

        toursList = hotelsViewModel.getAllTours();
        String[] items = new String[toursList.size()];

        for (Tours tour : toursList
        ) {
            items[i] = String.valueOf(tour.getTid()) + " " + tour.getCity();
            i++;
        }

        autocompleteText = binding.getRoot().findViewById(R.id.update_auto_complete_Htid);
        adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, items);

        autocompleteText.setAdapter(adapterItems);
        autocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        //Image Selection onClick

        binding.updatebrowseImageLibraryHotel.setOnClickListener(new View.OnClickListener() {
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
                binding.updateHotelpreview.setImageBitmap(bitmap);
            } catch (NullPointerException e) {
            }


        }
        if(uri!=null) {
            flag = true;
            Toast.makeText(getActivity(), "Image selected !", Toast.LENGTH_SHORT).show();

        }
    }

}
