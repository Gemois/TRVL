package com.iee.trvlapp.ui.more;


import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.iee.trvlapp.databinding.FragmentSettingsBinding;
import com.iee.trvlapp.util.ImportDataUtil;

public class Settings extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Listener Handlers for Database Actions regarding ImportData Class ( Prepopulate, Delete )
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.homeFillData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ImportDataUtil.populateRoomDatabase();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(getActivity(), "This action gets canceled because violates foreign keys !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.homedeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportDataUtil.deleteAllRoomApiData();
            }
        });


        binding.homeFillDatafs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportDataUtil.populateFirestoreDatabase();
            }
        });


        binding.homedeletefsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportDataUtil.deleteAllFirestoreData();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
