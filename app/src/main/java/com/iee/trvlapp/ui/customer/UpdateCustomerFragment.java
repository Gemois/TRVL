package com.iee.trvlapp.ui.customer;

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

import com.google.firebase.firestore.DocumentReference;
import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentUpdateCostumersBinding;
import com.iee.trvlapp.entities.CityHotel;
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

import java.util.List;

public class UpdateCustomerFragment extends Fragment {


    private FragmentUpdateCostumersBinding binding;

    ArrayAdapter<String> adapterItems;
    List<Package> packageList;
    List<CityHotel> hotelsList;
    ArrayAdapter<String> adapterHotelItems;

    AutoCompleteTextView autocompleteText;
    AutoCompleteTextView autocompleteHotelText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomerViewModel costumersViewModel =
                new ViewModelProvider(this).get(CustomerViewModel.class);

        binding = FragmentUpdateCostumersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Retrieves Data passed From Costumers Fragment
        int cid = UpdateCustomerFragmentArgs.fromBundle(getArguments()).getCustomerId();
        String name = UpdateCustomerFragmentArgs.fromBundle(getArguments()).getCustomerName();
        String surname = UpdateCustomerFragmentArgs.fromBundle(getArguments()).getSurname();
        long phone = Long.parseLong(UpdateCustomerFragmentArgs.fromBundle(getArguments()).getCustomerPhone());
        String email = UpdateCustomerFragmentArgs.fromBundle(getArguments()).getCustomerEmail();
        int pid = UpdateCustomerFragmentArgs.fromBundle(getArguments()).getCustomerPid();
        int hotel = UpdateCustomerFragmentArgs.fromBundle(getArguments()).getCustomerHotel();


        // Supports Dynamic autocomplete ListView for package_id on UpdateCostumer Fragment
        int i = 0;

        packageList = costumersViewModel.getAllPackages();
        String[] items = new String[packageList.size()];
        for (Package packages : packageList
        ) {
            Office curentOffice = costumersViewModel.getOfficeById(packages.getOfId());

            Tour currentTour = costumersViewModel.getTourById(packages.getTid());
            items[i] = String.valueOf(packages.getPid()) + " " + " | " + curentOffice.getName() + " | " + currentTour.getCity() + " | " + packages.getCost() + "$";
            i++;
        }


        autocompleteText = binding.getRoot().findViewById(R.id.auto_complete_costumer_pid2_update);
        adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, items);

        autocompleteText.setAdapter(adapterItems);
        autocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();


                // Supports Dynamic autocomplete ListView for hotel_id on UpdateCostumer Fragment
                int j = 0;

                Package packages = costumersViewModel.getPackageById(Integer.parseInt(item.substring(0, item.indexOf(" "))));
                hotelsList = costumersViewModel.getHotelsList(packages.getTid());

                String[] hotelItems = new String[hotelsList.size()];
                for (CityHotel cityHotel : hotelsList
                ) {
                    hotelItems[j] = String.valueOf(cityHotel.getHid() + " " + cityHotel.getHotelName());
                    j++;
                }

                autocompleteHotelText = binding.getRoot().findViewById(R.id.auto_complete_c_hotel_update);
                adapterHotelItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, hotelItems);

                autocompleteHotelText.setAdapter(adapterHotelItems);
                autocompleteHotelText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item2 = adapterView.getItemAtPosition(i).toString();
                    }
                });

            }
        });


        binding.updateFirstNameEdit.setText(name);
        binding.updateLastNameEdit.setText(surname);
        binding.updatePhoneEdit.setText(String.valueOf(phone));
        binding.updateEmailEdit.setText(email);


        //  Updates Chosen Costumer and Navigates to Costumers Fragment
        binding.updateCostumerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.updateFirstNameEdit.getText().toString();
                String surname = binding.updateLastNameEdit.getText().toString();
                String phone = binding.updatePhoneEdit.getText().toString();
                String email = binding.updateEmailEdit.getText().toString();

                if (binding.updateLastNameEdit.length() != 0 && binding.updateFirstNameEdit.length() != 0 && binding.updatePhoneEdit.length() != 0 && binding.updateEmailEdit.length() != 0) {

                    DocumentReference data = MainActivity.appDb.collection("costumers").document(String.valueOf(cid));

                    data.update("cid", cid);
                    data.update("name", name);
                    data.update("surname", surname);
                    data.update("email", email);
                    data.update("phone", Long.parseLong(phone));


                    String pidString = binding.autoCompleteCostumerPid2Update.getText().toString();
                    if (!pidString.isEmpty()) {
                        String pidCut = pidString.substring(0, pidString.indexOf(" "));
                        int pid = Integer.parseInt(pidCut);
                        data.update("pid", pid);
                    } else {
                        data.update("pid", pid);
                    }


                    String hidString = binding.autoCompleteCHotelUpdate.getText().toString();
                    if (!hidString.isEmpty()) {

                        String tidCut = hidString.substring(0, hidString.indexOf(" "));
                        int hid = Integer.parseInt(tidCut);
                        data.update("hotel", hid);
                    } else {
                        data.update("hotel", hotel);
                    }
                    Navigation.findNavController(view).navigate(R.id.action_updateCostumersFragment_to_nav_costumers2);
                } else {
                    Toast.makeText(getActivity(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Update Action is Canceled and Navigates to Costumers Fragment
        binding.updateCancelCostumerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_updateCostumersFragment_to_nav_costumers2);

            }
        });

        return root;
    }
}
