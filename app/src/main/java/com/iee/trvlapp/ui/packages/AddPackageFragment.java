package com.iee.trvlapp.ui.packages;

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
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

import java.util.List;

public class AddPackageFragment extends Fragment {

    private FragmentAddPackagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PackageViewModel packagesViewModel = new ViewModelProvider(this).get(PackageViewModel.class);

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

        List<Tour> toursList = packagesViewModel.getAllTours();
        String[] items = new String[toursList.size()];
        for (Tour tour : toursList) {
            items[i] = String.valueOf(tour.getTid()) + " " + tour.getCity();
            i++;
        }

        AutoCompleteTextView autocompleteText = binding.getRoot().findViewById(R.id.auto_complete_ptid);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, items);

        autocompleteText.setAdapter(adapterItems);
        autocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });


        // Supports Dynamic autocomplete ListView for officeList on AddPackage Fragment
        int j = 0;

        List<Office> officesList = packagesViewModel.getAllOffices();
        String[] officeItems = new String[officesList.size()];
        for (Office office : officesList) {
            officeItems[j] = String.valueOf(office.getOfId() + " " + office.getName());
            j++;
        }

        AutoCompleteTextView autocompleteOfficeText = binding.getRoot().findViewById(R.id.auto_complete_officeid);
        ArrayAdapter<String> adapterOfficeItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, officeItems);

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

    // Inserts Package
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

            Package Package = new Package();
            Package.setOfId(office_id);
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
