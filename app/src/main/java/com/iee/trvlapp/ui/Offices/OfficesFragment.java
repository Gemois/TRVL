package com.iee.trvlapp.ui.Offices;

import android.app.AlertDialog;
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
import com.google.firebase.firestore.Query;
import com.iee.trvlapp.FirestoreEntities.Costumers;
import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentOfficesBinding;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.ui.Costumers.CostumerRecyclerViewAdapter;

import java.util.List;

public class OfficesFragment extends Fragment {

    private FragmentOfficesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OfficesViewModel officesViewModel =
                new ViewModelProvider(this).get(OfficesViewModel.class);

        binding = FragmentOfficesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = binding.officeRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        final OfficeRecyclerViewAdapter adapter = new OfficeRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);


        //Retrieves and feeds the RecyclerViewAdapter with Offices Data

        officesViewModel.getAllOffices().observe(getViewLifecycleOwner(), new Observer<List<Offices>>() {
            @Override
            public void onChanged(List<Offices> offices) {
                adapter.setOffices(offices);
            }
        });


        //Deletes Office on Swipe LEFT


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                officesViewModel.deleteOffice(adapter.getOfficeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Office deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //  onClick preview of recyclerView Item

        adapter.setOnItemClickListener(new OfficeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Offices office) {
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                View dialogView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.offices_dialog_box, null);


                ImageView image = dialogView.findViewById(R.id.icon_dialog_office);
                TextView id = dialogView.findViewById(R.id.office_dialog_id);
                TextView name = dialogView.findViewById(R.id.office_dialog_name);
                TextView address = dialogView.findViewById(R.id.office_dialog_address);
                try {
                    image.setImageBitmap(DataConverter.convertByteArray2IMage(office.getImage()));
                } catch (NullPointerException e) {
                }
                id.setText(String.valueOf(office.getOfid()));
                name.setText(office.getName());
                address.setText(office.getAddress());
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });

        //Updates Office onLongClick

        adapter.setOnItemLongClickListener(new OfficeRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(Offices office) {
                int id = office.getOfid();
                String name = office.getName().toString();
                String address = office.getAddress().toString();
                NavDirections action = OfficesFragmentDirections.actionNavOfficesToUpdateOfficesFragment(id, name, address);
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }
        });


        //Navigates to the AddOffice Fragment

        binding.floatingActionButtonAddOffices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_offices_to_addOfficesFragment);
            }
        });


        // Gets support and populates menu for filter options

        binding.fabDeleteOffices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                officesViewModel.deleteAll();
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