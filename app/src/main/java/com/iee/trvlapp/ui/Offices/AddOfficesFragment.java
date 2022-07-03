package com.iee.trvlapp.ui.Offices;

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
import com.iee.trvlapp.databinding.FragmentAddOfficesBinding;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Offices;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AddOfficesFragment extends Fragment {

    private FragmentAddOfficesBinding binding;

    Bitmap bitmap = null;
    final int SELECT_PHOTO = 1;
    Uri uri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OfficesViewModel officesViewModel =
                new ViewModelProvider(this).get(OfficesViewModel.class);

        binding = FragmentAddOfficesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Calls function to handle Office Insertion

        binding.officeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertOfficeData();
            }
        });


        // Add Action is Canceled and Navigates to Offices Fragment

        binding.cancelUpdateOfficesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_addOfficesFragment_to_nav_offices);
            }
        });


        //Image Selection onClick

        binding.browseImageLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(view);

            }
        });

        return root;
    }


    //Inserts  Office
    public void insertOfficeData() {

        String office_name = binding.OfficeName.getText().toString();
        String office_address = binding.officeAddress.getText().toString();

        if (binding.OfficeName.length() !=0  && binding.officeAddress.length() != 0) {
            Offices office = new Offices();
            office.setName(office_name);
            office.setAddress(office_address);

            if (bitmap != null) {
                office.setImage(DataConverter.convertIMage2ByteArray(bitmap));
            }
            MainActivity.appDatabase.officesDao().addOffice(office);

            Toast.makeText(getActivity(), "Office added !", Toast.LENGTH_LONG).show();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addOfficesFragment_to_nav_offices);
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
            binding.landAddOfficeImagePreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
        }
        if(uri!=null) {
            Toast.makeText(getActivity(), "Image selected !", Toast.LENGTH_SHORT).show();
        }    }

}
