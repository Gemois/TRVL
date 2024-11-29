package com.iee.trvlapp.ui.packages;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iee.trvlapp.MainActivity;
import com.iee.trvlapp.R;
import com.iee.trvlapp.converters.DataConverter;
import com.iee.trvlapp.entities.Office;
import com.iee.trvlapp.entities.Package;
import com.iee.trvlapp.entities.Tour;

import java.util.ArrayList;
import java.util.List;

public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.PackageHolder> {

    private List<Package> packages = new ArrayList<>();
    private OnItemClickListener listener;
    private PackageRecyclerViewAdapter.OnItemLongClickListener longClickListener;

    public class PackageHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView ofid;
        private final TextView tid;
        private final TextView departure;
        private final TextView cost;
        private final TextView office_name;
        private final TextView tour_City;
        private final ImageView imageOffice;
        private final ImageView imageTour;

        public PackageHolder(View view) {
            super(view);
            id = view.findViewById(R.id.package_row_id);
            ofid = view.findViewById(R.id.package_row_ofid);
            tid = view.findViewById(R.id.package_row_tid);
            departure = view.findViewById(R.id.package_row_departure);
            cost = view.findViewById(R.id.package_row_cost);

            office_name = view.findViewById(R.id.package_row_t_name);
            tour_City = view.findViewById(R.id.package_row_t_city);
            imageOffice = view.findViewById(R.id.package_icon_row_office);
            imageTour = view.findViewById(R.id.package_icon_row_tour);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(packages.get(position));
                    }
                }
            });


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onLongClick(packages.get(position));

                    }
                    return false;
                }
            });

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPackages(List<Package> packages) {
        this.packages = packages;
        notifyDataSetChanged();
    }

    public Package getPackageAt(int position) {
        return packages.get(position);

    }

    @NonNull
    @Override
    public PackageRecyclerViewAdapter.PackageHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_packages_row, viewGroup, false);

        return new PackageRecyclerViewAdapter.PackageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageRecyclerViewAdapter.PackageHolder holder, int position) {
        Package currentPackage = packages.get(position);

        if (currentPackage != null) {
            Tour curentTour = MainActivity.appDatabase.toursDao().getTourById(currentPackage.getTid());
            Office currentOffice = MainActivity.appDatabase.officesDao().getOfficeById(currentPackage.getOfId());

            holder.id.setText(String.valueOf(currentPackage.getPid()));
            holder.ofid.setText(String.valueOf(currentPackage.getOfId()));
            holder.tid.setText(String.valueOf(currentPackage.getTid()));

            holder.departure.setText(String.valueOf(currentPackage.getDepartureTime()));
            holder.cost.setText(String.valueOf(currentPackage.getCost()));

            if (curentTour != null) {
                holder.tour_City.setText(curentTour.getCity());
            }
            if (currentOffice != null) {
                if (currentOffice.getImage() != null) {
                    holder.office_name.setText(currentOffice.getName());
                }
            }
            if (currentOffice != null) {
                if (currentOffice.getImage() != null) {
                    holder.imageOffice.setImageBitmap(DataConverter.convertByteArray2IMage(currentOffice.getImage()));
                }
            }
            if (curentTour != null) {
                if (curentTour.getImageTour() != null) {
                    holder.imageTour.setImageBitmap(DataConverter.convertByteArray2IMage(curentTour.getImageTour()));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Package packages);
    }

    public interface OnItemLongClickListener {
        void onLongClick(Package packages);
    }

    public void setOnItemClickListener(PackageRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(PackageRecyclerViewAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

}
