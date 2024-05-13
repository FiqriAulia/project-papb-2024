package com.example.tambahteman.friendrequest.progress5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tambahteman.R;
import com.example.tambahteman.friendrequest.FriendRequest;

import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
    private List<com.example.tambahteman.friendrequest.FriendRequest> friendRequestList;
    private Context context;

    public FriendRequestAdapter(Context context, List<com.example.tambahteman.friendrequest.FriendRequest> friendRequestList) {
        this.context = context;
        this.friendRequestList = friendRequestList;
    }

    // Inner class untuk ViewHolder
    public static class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageButton acceptButton;
        public ImageButton rejectButton;

        public FriendRequestViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            acceptButton = itemView.findViewById(R.id.acc);
            rejectButton = itemView.findViewById(R.id.tolak);
        }
    }

    @Override
    public FriendRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendRequestViewHolder holder, int position) {
        com.example.tambahteman.friendrequest.FriendRequest friendRequest = friendRequestList.get(holder.getAdapterPosition()); // Menggunakan holder.getAdapterPosition() untuk mendapatkan posisi yang benar
        holder.textViewName.setText(friendRequest.getUsername());

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementasi logika untuk menerima permintaan pertemanan
                // Misalnya, hapus permintaan dari daftar setelah diterima
                friendRequestList.remove(holder.getAdapterPosition()); // Menggunakan holder.getAdapterPosition() untuk mendapatkan posisi yang benar
                notifyDataSetChanged();
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementasi logika untuk menolak permintaan pertemanan
                // Misalnya, hapus permintaan dari daftar setelah ditolak
                friendRequestList.remove(holder.getAdapterPosition()); // Menggunakan holder.getAdapterPosition() untuk mendapatkan posisi yang benar
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendRequestList.size();
    }

    // Method untuk mengatur daftar teman permintaan yang difilter
    public void setFilteredList(List<FriendRequest> filteredList) {
        this.friendRequestList = filteredList;
        notifyDataSetChanged(); // Beritahu adapter bahwa dataset telah berubah
    }
}
