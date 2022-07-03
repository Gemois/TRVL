package com.iee.trvlapp.ui.Tours;

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

import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentToursBinding;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Tours;
import com.iee.trvlapp.ui.Offices.OfficesFragmentDirections;

import java.util.List;

public class ToursFragment extends Fragment {

    private FragmentToursBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ToursViewModel toursViewModel =
                new ViewModelProvider(this).get(ToursViewModel.class);

        binding = FragmentToursBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Retrieves and feeds the RecyclerViewAdapter with Tours Data

        RecyclerView recyclerView = binding.tourRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        final TourRecyclerViewAdapter adapter = new TourRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        toursViewModel.getAllTours().observe(getViewLifecycleOwner(), new Observer<List<Tours>>() {
            @Override
            public void onChanged(List<Tours> tours) {
                adapter.setTours(tours);
            }
        });


        // Navigates to TourMap Fragment on Swipe RIGHT and shows the corresponding location


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                String CityName = adapter.getTourAt(viewHolder.getAbsoluteAdapterPosition()).getCity();
                NavDirections action = ToursFragmentDirections.actionNavToursToTourMapsFragment(CityName);
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }
        }).attachToRecyclerView(recyclerView);


        //Deletes Tour on Swipe LEFT

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                toursViewModel.deleteTour(adapter.getTourAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Tour deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //  onClick preview of recyclerView Item

        adapter.setOnItemClickListener(new TourRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tours tour) {
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                View dialogView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.tours_dialog_box, null);


                ImageView image = dialogView.findViewById(R.id.tour_dialog_image);
                TextView id = dialogView.findViewById(R.id.tour_dialog_id);
                TextView city = dialogView.findViewById(R.id.tour_dialog_city);
                TextView country = dialogView.findViewById(R.id.tour_dialog_country);
                TextView duration = dialogView.findViewById(R.id.tour_dialog_duration);
                TextView type = dialogView.findViewById(R.id.tour_dialog_type);

                try {
                    image.setImageBitmap(DataConverter.convertByteArray2IMage(tour.getImageTour()));
                } catch (NullPointerException e) {
                }
                id.setText(String.valueOf(tour.getTid()));
                city.setText(tour.getCity());
                country.setText(tour.getCountry());
                duration.setText(String.valueOf(tour.getDuration())+" days");
                type.setText(tour.getType());

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });


        //Updates Tour onLongClick

        adapter.setOnItemLongClickListener(new TourRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(Tours tours) {
                int id = tours.getTid();
                String city = tours.getCity().toString();
                String country = tours.getCountry().toString();
                int duration = tours.getDuration();
                String type = tours.getType().toString();

                NavDirections action = ToursFragmentDirections.actionNavToursToUpdateToursFragment(id, city, country,duration,type);
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }
        });


        //Navigates to the AddTour Fragment

        binding.floatingActionButtonAddTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_tours_to_addToursFragment);
            }
        });

        binding.fabDeleteAllTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toursViewModel.deleteAll();
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