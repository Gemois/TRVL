package com.iee.trvlapp.ui.Packages;

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

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.databinding.FragmentPackagesBinding;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Offices;
import com.iee.trvlapp.roomEntities.Packages;
import com.iee.trvlapp.roomEntities.Tours;
import com.iee.trvlapp.ui.Offices.OfficesFragmentDirections;

import java.util.List;

public class PackagesFragment extends Fragment {

    private FragmentPackagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PackagesViewModel packagesViewModel =
                new ViewModelProvider(this).get(PackagesViewModel.class);

        binding = FragmentPackagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Retrieves and feeds the RecyclerViewAdapter with Packages Data

        RecyclerView recyclerView = binding.packageRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        final PackageRecyclerViewAdapter adapter = new PackageRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        packagesViewModel.getAllPackages().observe(getViewLifecycleOwner(), new Observer<List<Packages>>() {
            @Override
            public void onChanged(List<Packages> packages) {
                adapter.setPackages(packages);
            }
        });


        //Deletes Package on Swipe LEFT

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                packagesViewModel.deletePackage(adapter.getPackageAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Package deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //  onClick preview of recyclerView Item

        adapter.setOnItemClickListener(new PackageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Packages packages) {
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                View dialogView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.packages_dialog_box, null);


                ImageView imageOffice = dialogView.findViewById(R.id.package_icon_dialog_office);
                ImageView imageTour = dialogView.findViewById(R.id.package_dialog_row_tour);
                TextView pid = dialogView.findViewById(R.id.package_dialog_id);
                TextView ofid = dialogView.findViewById(R.id.package_dialog_ofid);
                TextView tid = dialogView.findViewById(R.id.package_dialog_tid);
                TextView ofname = dialogView.findViewById(R.id.package_dialog_of_name);
                TextView city = dialogView.findViewById(R.id.package_dialog_t_city);
                TextView duration = dialogView.findViewById(R.id.package_dialog_departure);
                TextView cost = dialogView.findViewById(R.id.package_dialog_cost);


                Tours curentTour = MainActivity.appDatabase.toursDao().getTourById(packages.getTid());
                Offices curentOffice = MainActivity.appDatabase.officesDao().getOfficeById(packages.getOfid());
                try {
                    imageOffice.setImageBitmap(DataConverter.convertByteArray2IMage(curentOffice.getImage()));
                } catch (NullPointerException e) {
                }
                try {
                    imageTour.setImageBitmap(DataConverter.convertByteArray2IMage(curentTour.getImageTour()));
                } catch (NullPointerException e) {
                }
                pid.setText(String.valueOf(packages.getPid()));
                ofid.setText(String.valueOf(packages.getOfid()));
                tid.setText(String.valueOf(packages.getTid()));
                ofname.setText(String.valueOf(curentOffice.getName()));
                city.setText(curentTour.getCity());
                duration.setText(packages.getDepartureTime());
                cost.setText(String.valueOf(packages.getCost()));

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });


        //Updates Package onLongClick

        adapter.setOnItemLongClickListener(new PackageRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(Packages packages) {
                int id = packages.getPid();
                int ofid = packages.getOfid();
                int tid = packages.getTid();
                String departure = packages.getDepartureTime();
                String cost = String.valueOf(packages.getCost());

                NavDirections action = PackagesFragmentDirections.actionNavPackagesToUpdatePackagesFragment(id, ofid, tid, departure, cost);
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }
        });


        //Navigates to the AddPackage Fragment

        binding.floatingActionButtonAddPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_packages_to_addPackagesFragment);
            }
        });

        binding.fabDeletePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packagesViewModel.deleteAll();
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
