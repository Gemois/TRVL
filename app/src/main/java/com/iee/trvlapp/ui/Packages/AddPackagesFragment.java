package com.iee.trvlapp.ui.Packages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentAddPackagesBinding;

import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Packages;
import com.iee.trvlapp.roomEntities.Tours;

import java.util.List;

public class AddPackagesFragment extends Fragment {

    private FragmentAddPackagesBinding binding;

    AutoCompleteTextView autocompleteText;
    AutoCompleteTextView autocompleteOfficeText;

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterOfficeItems;

    List<Tours> toursList;
    List<Offices> officesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PackagesViewModel packagesViewModel =
                new ViewModelProvider(this).get(PackagesViewModel.class);

        binding = FragmentAddPackagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Calls function to handle Package Insertion

        binding.packageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPackageData();
            }
        });

        // Supports Dynamic autocomplete ListView for tourList on AddPackage Fragment

        int i = 0;

        toursList = packagesViewModel.getAllTours();
        String[] items = new String[toursList.size()];
        for (Tours tour : toursList
        ) {
            items[i] = String.valueOf(tour.getTid()) + " " + tour.getCity();
            i++;
        }

        autocompleteText = binding.getRoot().findViewById(R.id.auto_complete_ptid);
        adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, items);

        autocompleteText.setAdapter(adapterItems);
        autocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });


        // Supports Dynamic autocomplete ListView for officeList on AddPackage Fragment


        int j = 0;

        officesList = packagesViewModel.getAllOffices();
        String[] officeItems = new String[officesList.size()];
        for (Offices office : officesList
        ) {
            officeItems[j] = String.valueOf(office.getOfid() + " " + office.getName());
            j++;
        }

        autocompleteOfficeText = binding.getRoot().findViewById(R.id.auto_complete_officeid);
        adapterOfficeItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, officeItems);

        autocompleteOfficeText.setAdapter(adapterOfficeItems);
        autocompleteOfficeText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });


        // Add Action is Canceled and Navigates to Packages Fragment

        binding.cancelUpdatePackagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_addPackagesFragment_to_nav_packages);
            }
        });


        return root;
    }

    //Inserts  Package

    public void insertPackageData() {
        int tour_id = 0;
        int office_id = 0;
        String tour_idString = binding.autoCompletePtid.getText().toString();
        if (!tour_idString.isEmpty()) {
            String tour_idCut = tour_idString.substring(0, tour_idString.indexOf(" "));
            tour_id = Integer.parseInt(tour_idCut);
        }
        String office_idString = binding.autoCompleteOfficeid.getText().toString();
        if (!office_idString.isEmpty()) {
            String office_idCut = office_idString.substring(0, office_idString.indexOf(" "));
            office_id = Integer.parseInt(office_idCut);
        }

        String package_departure = binding.packageDeparture.getText().toString();
        String package_cost = binding.packageCost.getText().toString();

        if (binding.autoCompletePtid.length() != 0 && binding.autoCompleteOfficeid.length() != 0 && binding.packageDeparture.length() != 0 && binding.packageCost.length() != 0) {

            Packages Package = new Packages();
            Package.setOfid(office_id);
            Package.setTid(tour_id);
            Package.setDepartureTime(package_departure);
            Package.setCost(Double.parseDouble(package_cost));

            MainActivity.appDatabase.packagesDao().addPackage(Package);

            Toast.makeText(getActivity(), "Package added !", Toast.LENGTH_LONG).show();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addPackagesFragment_to_nav_packages);
        } else {
            Toast.makeText(getActivity(), "fill all the fields", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
