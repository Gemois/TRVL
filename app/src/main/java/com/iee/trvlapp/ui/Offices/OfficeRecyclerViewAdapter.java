package com.iee.trvlapp.ui.Offices;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iee.trvlapp.R;
import com.iee.trvlapp.roomEntities.DataConverter;
import com.iee.trvlapp.roomEntities.Offices;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OfficeRecyclerViewAdapter extends RecyclerView.Adapter<OfficeRecyclerViewAdapter.OfficeHolder> {

    private List<Offices> offices = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public class OfficeHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView address;
        ImageView officeImage;

        public OfficeHolder(View view) {
            super(view);
            id = view.findViewById(R.id.office_row_id);
            name = view.findViewById(R.id.office_row_name);
            address = view.findViewById(R.id.office_row_address);
            officeImage = view.findViewById(R.id.icon_row_office);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(offices.get(position));
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onLongClick(offices.get(position));

                    }
                    return false;
                }
            });



        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setOffices(List<Offices> offices) {
        this.offices = offices;
        notifyDataSetChanged();
    }


    public Offices getOfficeAt(int position) {
        return offices.get(position);

    }


    @NonNull
    @Override
    public OfficeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_offices_row, viewGroup, false);

        return new OfficeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficeHolder holder, int position) {
        Offices currentOffice = offices.get(position);
        holder.id.setText(String.valueOf(currentOffice.getOfid()));
        holder.name.setText(currentOffice.getName());
        holder.address.setText(currentOffice.getAddress());
        if (currentOffice.getImage() != null) {
            holder.officeImage.setImageBitmap(DataConverter.convertByteArray2IMage(currentOffice.getImage()));
        }
    }

    @Override
    public int getItemCount() {
        return offices.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Offices office);
    }
    public interface OnItemLongClickListener{
        void onLongClick(Offices office);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){this.longClickListener=longClickListener;}
}


