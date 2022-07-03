package com.iee.trvlapp.ui.Hotels;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.iee.trvlapp.databinding.FragmentHotelsBinding;
import com.iee.trvlapp.roomEntities.CityHotels;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Tours;
import com.iee.trvlapp.ui.Packages.PackagesFragmentDirections;

import java.util.List;

public class HotelsFragment extends Fragment {

    private FragmentHotelsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HotelsViewModel hotelsViewModel =
                new ViewModelProvider(this).get(HotelsViewModel.class);

        binding = FragmentHotelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.hotelRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        final HotelRecyclerViewAdapter adapter = new HotelRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        // Retrieves and feeds the RecyclerViewAdapter with CityHotels Data

        hotelsViewModel.getHotelsOrderByStarsDesc().observe(getViewLifecycleOwner(), new Observer<List<CityHotels>>() {
            @Override
            public void onChanged(List<CityHotels> hotels) {
                adapter.setHotels(hotels);
            }
        });


        //Deletes CityHotel on Swipe LEFT

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                hotelsViewModel.deleteHotel(adapter.getHotelAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Hotel deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //  onClick preview of recyclerView Item
        adapter.setOnItemClickListener(new HotelRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CityHotels hotel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                View dialogView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.hotels_dialog_box, null);


                ImageView image = dialogView.findViewById(R.id.icon_dialog_hotel);
                TextView id = dialogView.findViewById(R.id.hotel_dialog_id);
                TextView name = dialogView.findViewById(R.id.hotel_dialog_name);
                TextView address = dialogView.findViewById(R.id.hotel_dialog_address);
                TextView stars = dialogView.findViewById(R.id.hotel_dialog_stars);
                TextView tourId = dialogView.findViewById(R.id.hotel_dialog_tid);
                TextView city = dialogView.findViewById(R.id.hotel_dialog_city);
                try {
                    image.setImageBitmap(DataConverter.convertByteArray2IMage(hotel.getImageHotel()));
                } catch (NullPointerException e) {
                }
                try {
                    Tours currentTour = hotelsViewModel.getTourById(hotel.getTid());
                    city.setText(currentTour.getCity());
                } catch (NullPointerException e) {
                }

                id.setText(String.valueOf(hotel.getHid()));
                name.setText(hotel.getHotelName());
                address.setText(hotel.getHotelAddress());
                stars.setText(String.valueOf(hotel.getHotelStars()));
                tourId.setText(String.valueOf(hotel.getTid()));

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });


        //Updates CityHotel onLongClick

        adapter.setOnItemLongClickListener(new HotelRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(CityHotels hotel) {
                int id = hotel.getHid();
                String name = hotel.getHotelName();
                String address = hotel.getHotelAddress();
                int stars = hotel.getHotelStars();
                int tid = hotel.getTid();

                NavDirections action = HotelsFragmentDirections.actionNavHotelsToUpdateHotelsFragment(id, name, address, stars, tid);
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }
        });


        //Navigates to the AddHotel Fragment

        binding.floatingActionButtonAddHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_hotels_to_addHotelsFragment);
            }
        });


        binding.fabDeleteHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotelsViewModel.deleteAll();
            }
        });

        return root;
    }

}
