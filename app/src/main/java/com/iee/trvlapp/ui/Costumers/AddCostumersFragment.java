package com.iee.trvlapp.ui.Costumers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.iee.trvlapp.FirestoreEntities.Costumers;
import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentAddCostumersBinding;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Packages;
import com.iee.trvlapp.roomEntities.Tours;

import java.util.List;


public class AddCostumersFragment extends Fragment {

    private FragmentAddCostumersBinding binding;

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterHotelItems;

    AutoCompleteTextView autocompleteText;
    AutoCompleteTextView autocompleteHotelText;

    List<Packages> packageList;
    List<CityHotels> hotelsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CostumersViewModel costumersViewModel =
                new ViewModelProvider(this).get(CostumersViewModel.class);

        binding = FragmentAddCostumersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Calls function to handle Costumer Insertion

        binding.costumerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCostumersData();
            }
        });


        // Supports Dynamic autocomplete ListView for package_id on UpdateCostumer Fragment

        int i = 0;

        packageList = costumersViewModel.getAllPackages();
        String[] items = new String[packageList.size()];
        int[] itemsId = new int[packageList.size()];
        for (Packages packages : packageList
        ) {
            Offices curentOffice = costumersViewModel.getOfficeById(packages.getOfid());
            Tours currentTour = costumersViewModel.getTourById(packages.getTid());
            items[i] = String.valueOf(packages.getPid()) + " " +" | "+ curentOffice.getName() +" | " + currentTour.getCity() + " | " + packages.getCost() + "$";
            i++;
        }


        autocompleteText = binding.getRoot().findViewById(R.id.auto_complete_costumer_pid2);
        adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, items);
        autocompleteText.setAdapter(adapterItems);
        autocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                // Supports Dynamic autocomplete ListView for hotel_id on UpdateCostumer Fragment

                int j = 0;

                Packages packages = costumersViewModel.getPackageById(Integer.parseInt(item.substring(0, item.indexOf(" "))));

                hotelsList = costumersViewModel.getHotelsList(packages.getTid());
                String[] hotelItems = new String[hotelsList.size()];
                for (CityHotels cityHotel : hotelsList
                ) {
                    hotelItems[j] = String.valueOf(cityHotel.getHid() + " " + cityHotel.getHotelName());
                    j++;
                }

                autocompleteHotelText = binding.getRoot().findViewById(R.id.auto_complete_c_hotel);
                adapterHotelItems = new ArrayAdapter<String>(getActivity(), R.layout.auto_complete_list_item, hotelItems);
                autocompleteHotelText.setAdapter(adapterHotelItems);
                autocompleteHotelText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item2 = adapterView.getItemAtPosition(i).toString();


                    }
                });

                //Irritates Firestore Collection and Retrieves the Collection Size for Auto Increment Support

                MainActivity.appDb
                        .collection("costumers")
                        .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    int size = 1;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Costumers costumers = documentSnapshot.toObject(Costumers.class);
                        size++;
                    }
                    binding.costumerId.setText(String.valueOf(size));
                });


            }
        });


        // Add Action is Canceled and Navigates to Hotels Fragment

        binding.cancelUpdateCostumersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_addCostumersFragment_to_nav_costumers);
            }
        });


        return root;
    }


    //Inserts  Costumer

    public void insertCostumersData() {

        if (binding.firstNameEdit.length() != 0 && binding.lastNameEdit.length() != 0 && binding.phoneEdit.length() != 0 && binding.emailEdit.length() != 0 && binding.autoCompleteCostumerPid2.length() != 0 && binding.autoCompleteCHotel.length() != 0) {

            String costumer_name = binding.firstNameEdit.getText().toString();
            String costumer_surname = binding.lastNameEdit.getText().toString();
            long costumer_phone = Long.parseLong(binding.phoneEdit.getText().toString());
            String costumer_email = binding.emailEdit.getText().toString();

            int package_id = 0;
            int hotel_id = 0;
            String package_idString = binding.autoCompleteCostumerPid2.getText().toString();
            if (!package_idString.isEmpty()) {
                String package_idCut = package_idString.substring(0, package_idString.indexOf(" "));
                package_id = Integer.parseInt(package_idCut);
            }
            String hotel_idString = binding.autoCompleteCHotel.getText().toString();
            if (!hotel_idString.isEmpty()) {
                String hotel_idCut = hotel_idString.substring(0, hotel_idString.indexOf(" "));
                hotel_id = Integer.parseInt(hotel_idCut);
            }

            String costumer_id = binding.costumerId.getText().toString();
            Costumers costumer = new Costumers();
            costumer.setCid(Integer.parseInt(costumer_id));
            costumer.setName(costumer_name);
            costumer.setSurname(costumer_surname);
            costumer.setPhone(costumer_phone);
            costumer.setEmail(costumer_email);
            costumer.setPid(package_id);
            costumer.setHotel(hotel_id);

            MainActivity.appDb.collection("costumers")
                    .document(costumer_id)
                    .set(costumer)
                    .addOnCompleteListener((task) -> {
                        Toast.makeText(getActivity(), "data added on firestore", Toast.LENGTH_LONG).show();

                        pushNotification(costumer);

                    })
                    .addOnFailureListener((e) -> {
                        Toast.makeText(getActivity(), "failed to add data on firestore", Toast.LENGTH_LONG).show();
                    });

            binding.firstNameEdit.setText("");
            binding.lastNameEdit.setText("");
            binding.emailEdit.setText("");
            binding.phoneEdit.setText("");

            Toast.makeText(getActivity(), "Costumer Added", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addCostumersFragment_to_nav_costumers);


        } else {
            Toast.makeText(getActivity(), "fill all fields", Toast.LENGTH_SHORT).show();
        }
    }


    // Push Notification when Costumer is inserted Successfully

    public void pushNotification(Costumers costumer) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notificationId", "notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "notificationId")
                .setContentText("TRVl")
                .setSmallIcon(R.drawable.ic_baseline_person_add_alt_1_24)
                .setAutoCancel(true)
                .setContentText("New Costumer added on Firestore!!!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(costumer.toString()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(999, builder.build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
