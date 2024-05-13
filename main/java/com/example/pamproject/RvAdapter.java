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
    private final List<friendlist> friendlistLists;
//    private final List<Friend> friendList;

    private final RvInteface rvInteface;

    public RvAdapter(Context context, List<friendlist> friendlistLists, RvInteface inteface) {
        this.context = context;
        this.friendlistLists = friendlistLists;
        this.rvInteface = inteface;
    }
//    public RvAdapter(Context context, List<Friend> friendList, RvInteface rvInterface) {
//        this.context = context;
//        this.friendList = friendList;
//        this.rvInterface = rvInterface;
//    }

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
        friendlist k = this.friendlistLists.get(position);
        holder.binding.tvnama.setText(k.getNama());
        holder.binding.tvdeskripsi.setText(k.getDeskripsi());
        if (k.getFoto() != null) {
            Glide.with(context)
                    .load(Base64.decode(k.getFoto(), Base64.DEFAULT))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                    .into(holder.binding.tvimage);
        } else {
            Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
        }

        holder.binding.tvimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvInteface.onitemclick(friendlistLists.get(position));
            }
        });
    }

//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Friend friend = friendList.get(position);
//        holder.binding.tvnama.setText(friend.getName());
//        holder.binding.tvdeskripsi.setText(friend.getDescription());
////        if (friend.getPhoto() != null) {
////            Glide.with(context)
////                    .load(friend.getPhoto())
////                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
////                    .into(holder.binding.tvimage);
////        } else {
////            Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
////        }
//        int imageResourceId = context.getResources().getIdentifier(friend.getPhoto(), "drawable", context.getPackageName());
//        if (imageResourceId != 0) {
//            // Mengatur gambar ke ImageView jika ID gambar ditemukan
//            holder.binding.tvimage.setImageResource(imageResourceId);
//        } else {
//            Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
//        }
//
//        holder.binding.tvimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rvInterface.onItemClick(friend);
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return this.friendlistLists.size();
    }
//    public int getItemCount() {
//        return friendList.size();
//    }
}