package com.iee.trvlapp.ui.Costumers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.core.OrderBy;
import com.iee.trvlapp.FirestoreEntities.Costumers;
import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentCostumersBinding;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Packages;
import com.iee.trvlapp.roomEntities.Tours;
import com.iee.trvlapp.ui.Offices.OfficeRecyclerViewAdapter;
import com.iee.trvlapp.ui.Offices.OfficesFragmentDirections;
import com.iee.trvlapp.ui.Offices.OfficesViewModel;
import com.iee.trvlapp.ui.SupportingClasses.ImportData;

import java.util.ArrayList;
import java.util.List;


public class CostumersFragment extends Fragment {
    CostumerRecyclerViewAdapter adapter;
    private FragmentCostumersBinding binding;
    private List<Costumers> costumerList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CostumersViewModel costumersViewModel =
                new ViewModelProvider(this).get(CostumersViewModel.class);

        binding = FragmentCostumersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Retrieves and feeds the RecyclerViewAdapter with Costumer Data ( Firestore )

        Query query = (Query) MainActivity.appDb.collection("costumers").orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Costumers> options = new FirestoreRecyclerOptions.Builder<Costumers>()
                .setQuery(query, Costumers.class).build();

        adapter = new CostumerRecyclerViewAdapter(options);
        RecyclerView recyclerView = binding.costumersRecyclerview;
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        //Deletes Costumer on Swipe LEFT

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);


        //  onClick preview of recyclerView Item

        adapter.setOnItemClickListener(new CostumerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                View dialogView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.customers_dialog_box, null);

                ImageView imageHotel = dialogView.findViewById(R.id.costumer_hotel_dialog);
                TextView cid = dialogView.findViewById(R.id.costumer_dialog_id);
                TextView name = dialogView.findViewById(R.id.costumer_dialog_name);
                TextView surname = dialogView.findViewById(R.id.costumer_dialog_surname);
                TextView phone = dialogView.findViewById(R.id.costumer_dialog_phone);
                TextView email = dialogView.findViewById(R.id.costumer_dialog_email);
                TextView pid = dialogView.findViewById(R.id.costumer_dialog_pid);
                TextView city = dialogView.findViewById(R.id.costumer_dialog_city);
                TextView hotel = dialogView.findViewById(R.id.costumer_dialog_hotel);

                Costumers costumer = documentSnapshot.toObject(Costumers.class);

                CityHotels curentHotel = MainActivity.appDatabase.cityHotelsDao().getCityHotelById(costumer.getHotel());
                Packages packages = MainActivity.appDatabase.packagesDao().getPackageById(costumer.getPid());
                Tours curentTour = MainActivity.appDatabase.toursDao().getTourById(packages.getTid());
                try {
                    imageHotel.setImageBitmap(DataConverter.convertByteArray2IMage(curentHotel.getImageHotel()));
                } catch (NullPointerException e) {
                }
                cid.setText(String.valueOf(costumer.getCid()));
                name.setText(costumer.getName());
                surname.setText(costumer.getSurname());
                phone.setText(String.valueOf(costumer.getPhone()));
                email.setText(costumer.getEmail());
                pid.setText(String.valueOf(costumer.getPid()));
                city.setText(curentTour.getCity());
                hotel.setText(curentHotel.getHotelName());

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });


        //Updates Costumer onLongClick

        adapter.setOnItemLongClickListener(new CostumerRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(DocumentSnapshot documentSnapshot, int position) {
                Costumers costumer = documentSnapshot.toObject(Costumers.class);
                int cid = costumer.getCid();
                String name = costumer.getName();
                String surname = costumer.getSurname();
                long phone = costumer.getPhone();
                String email = costumer.getEmail();
                int pid = costumer.getPid();
                int hotel = costumer.getHotel();

                NavDirections action = CostumersFragmentDirections.actionNavCostumersToUpdateCostumersFragment2(cid, name, surname, String.valueOf(phone), email, pid, hotel);
                Navigation.findNavController(binding.getRoot()).navigate(action);

            }
        });


        //Navigates to the AddCostumer Fragment

        binding.floatingActionButtonAddCostumers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_costumers_to_addCostumersFragment);
            }
        });

        binding.fabDeleteCostumers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportData.delteAllFirestoreData();
            }
        });


        return root;
    }


    // Bug handling when fragment onPause ( app crashes when phone is locked )

    @Override
    public void onPause() {
        super.onPause();
        Query query = (Query) MainActivity.appDb.collection("costumers");

        FirestoreRecyclerOptions<Costumers> options = new FirestoreRecyclerOptions.Builder<Costumers>()
                .setQuery(query, Costumers.class).build();

        adapter = new CostumerRecyclerViewAdapter(options);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
