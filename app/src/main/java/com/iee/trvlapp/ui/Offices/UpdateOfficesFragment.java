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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentUpdateOfficesBinding;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Offices;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UpdateOfficesFragment extends Fragment {
    private AppBarConfiguration mAppBarConfiguration;
    Bitmap bitmap = null;
    final int SELECT_PHOTO = 1;
    Uri uri;
    boolean flag = false;
    private FragmentUpdateOfficesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OfficesViewModel officesViewModel =
                new ViewModelProvider(this).get(OfficesViewModel.class);

        binding = FragmentUpdateOfficesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Retrieves Data passed From Office Fragment

        int id = UpdateOfficesFragmentArgs.fromBundle(getArguments()).getOfficeId();
        String name = UpdateOfficesFragmentArgs.fromBundle(getArguments()).getOfficeName();
        String address = UpdateOfficesFragmentArgs.fromBundle(getArguments()).getOfficeAddress();
        byte [] image=officesViewModel.getOfficeById(id).getImage();
        binding.updateOfficeName.setText(name);
        binding.updateOfficeAddress.setText(address);


        //  Updates Chosen Office and Navigates to Office Fragment

        binding.updateOfficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.updateOfficeName.getText().toString();
                String address = binding.updateOfficeAddress.getText().toString();


                if (binding.updateOfficeName.length() != 0 && binding.updateOfficeAddress.length() != 0) {

                    Offices office = new Offices();
                    office.setOfid(id);
                    office.setName(name);
                    office.setAddress(address);
                    if (flag) {
                        office.setImage(DataConverter.convertIMage2ByteArray(bitmap));
                    } else {
                        office.setImage(image);
                    }
                    officesViewModel.updateOffice(office);
                    Navigation.findNavController(view).navigate(R.id.action_updateOfficesFragment_to_nav_offices);
                } else {
                    Toast.makeText(getActivity(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Update Action is Canceled and Navigates to Office Fragment

        binding.cancelUpdateOfficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_updateOfficesFragment_to_nav_offices);

            }
        });

        //Image Selection onClick

        binding.updatebrowseImageLibrary.setOnClickListener(new View.OnClickListener() {
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
                binding.updateOfficePreview.setImageBitmap(bitmap);
            } catch (NullPointerException e) {
            }


        }
        if(uri!=null) {
            flag = true;
            Toast.makeText(getActivity(), "Image selected !", Toast.LENGTH_SHORT).show();

        }
    }

}
