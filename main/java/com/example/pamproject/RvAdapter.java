package com.example.pamproject;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.pamproject.databinding.RvLayoutBinding;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private final Context context;
    private final List<friendlist> friendList;
    private final RvInteface rvInterface;

    public RvAdapter(Context context, List<friendlist> friendList, RvInteface rvInterface) {
        this.context = context;
        this.friendList = friendList;
        this.rvInterface = rvInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RvLayoutBinding binding;

        public ViewHolder(RvLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutBinding binding = RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        friendlist friend = this.friendList.get(position);
        holder.binding.tvnama.setText(friend.getNama());
        holder.binding.tvdeskripsi.setText(friend.getDeskripsi());

        if (friend.getFotoid() != null && !friend.getFotoid().isEmpty()) {
            Glide.with(context)
                    .load(Base64.decode(friend.getFotoid(), Base64.DEFAULT))
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.binding.tvimage);
        } else {
            Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvInterface.onItemClick(friendList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.friendList.size();
    }
}
