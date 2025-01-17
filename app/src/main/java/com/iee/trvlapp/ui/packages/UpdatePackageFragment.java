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

import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentUpdatePackagesBinding;
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

import java.util.List;

public class UpdatePackageFragment extends Fragment {

    private FragmentUpdatePackagesBinding binding;
    AutoCompleteTextView autocompleteText;
    AutoCompleteTextView autocompleteOfficeText;

    ArrayAdapter<String> adapterItems;
    List<Tour> toursList;
    List<Office> officesList;
    ArrayAdapter<String> adapterOfficeItems;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PackageViewModel packagesViewModel =
                new ViewModelProvider(this).get(PackageViewModel.class);

        binding = FragmentUpdatePackagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Retrieves Data passed From Packages Fragment


        int id = UpdatePackageFragmentArgs.fromBundle(getArguments()).getPackageId();
        int office_id = UpdatePackageFragmentArgs.fromBundle(getArguments()).getPackageOfid();
        int tour_id = UpdatePackageFragmentArgs.fromBundle(getArguments()).getPackageTid();
        String departure = UpdatePackageFragmentArgs.fromBundle(getArguments()).getPackageDuration();
       double cost = Double.parseDouble(UpdatePackageFragmentArgs.fromBundle(getArguments()).getPackageCost());

        // Supports Dynamic autocomplete ListView for tour_id on UpdatePackage Fragment

        int i = 0;

        toursList = packagesViewModel.getAllTours();
        String[] items = new String[toursList.size()];

        for (Tour tour : toursList
        ) {
            items[i] = String.valueOf(tour.getTid()) + " " + tour.getCity();
            i++;
        }

        autocompleteText = binding.getRoot().findViewById(R.id.auto_complete_ptid_update);
        adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, items);

        autocompleteText.setAdapter(adapterItems);
        autocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        // Supports Dynamic autocomplete ListView for office_id on UpdatePackage Fragment

        int j = 0;

        officesList = packagesViewModel.getAllOffices();
        String[] officeItems = new String[officesList.size()];
        for (Office office : officesList
        ) {
            officeItems[j] = String.valueOf(office.getOfId() + " " + office.getName());
            j++;
        }

        autocompleteOfficeText = binding.getRoot().findViewById(R.id.auto_complete_officeid_update);
        adapterOfficeItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, officeItems);

        autocompleteOfficeText.setAdapter(adapterOfficeItems);
        autocompleteOfficeText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });


        binding.updatePackageDeparture.setText(String.valueOf(departure));
        binding.updatePackageCost.setText(String.valueOf(cost));


        //  Updates Chosen Package and Navigates to Packages Fragment

        binding.updatePackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.updatePackageDeparture.length() != 0 && binding.updatePackageCost.length() != 0) {

                    String departure = binding.updatePackageDeparture.getText().toString();
                    Double cost = Double.parseDouble(binding.updatePackageCost.getText().toString());

                    Package packages = new Package();
                    packages.setPid(id);

                    String office_idString = binding.autoCompleteOfficeidUpdate.getText().toString();
                    if (!office_idString.isEmpty()) {
                        String office_idCut = office_idString.substring(0, office_idString.indexOf(" "));
                        int office_id = Integer.parseInt(office_idCut);
                        packages.setOfId(office_id);
                    } else {
                        packages.setOfId(office_id);
                    }


                    String tour_idString = binding.autoCompletePtidUpdate.getText().toString();
                    if (!tour_idString.isEmpty()) {
                        String tour_idCut = tour_idString.substring(0, tour_idString.indexOf(" "));
                        int tour_id = Integer.parseInt(tour_idCut);
                        packages.setTid(tour_id);
                    } else {
                        packages.setTid(tour_id);
                    }

                    packages.setDepartureTime(departure);
                    packages.setCost(cost);
                    packagesViewModel.updatePackage(packages);
                    Toast.makeText(getActivity(), "Package updated !", Toast.LENGTH_SHORT).show();

                    Navigation.findNavController(view).navigate(R.id.action_updatePackagesFragment_to_nav_packages);
                } else {
                    Toast.makeText(getActivity(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


        // Update Action is Canceled and Navigates to Packages Fragment

        binding.cancelUpdatePackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_updatePackagesFragment_to_nav_packages);
            }
        });


        return root;
    }
}
