package com.example.pamproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private  RvInteface rvInteface;
    private final Context context;
    private final List<friendlist> friendlistLists;

    public RvAdapter(Context context, List<friendlist> friendlistLists,
                     RvInteface inteface){
        this.context=context;
        this.friendlistLists=friendlistLists;
        this.rvInteface=inteface;
    }
    public class VH extends RecyclerView.ViewHolder{
        private final ImageView tvimage;
        private TextView tvnama,tvdeskripsi;

        @SuppressLint("ResourceType")
        public VH(View itemView) {
            super(itemView);
            this.tvimage = itemView.findViewById(R.id.tvimage);
            this.tvnama = itemView.findViewById(R.id.tvnama);
            this.tvdeskripsi = itemView.findViewById(R.id.tvdeskripsi);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(this.context).inflate(R.layout.rv_layout, parent, false);

        return new VH(vh);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        friendlist k = this.friendlistLists.get(position);
        VH vh = (VH) holder;
        vh.tvnama.setText(k.getNama());
        vh.tvdeskripsi.setText(k.getDeskripsi());
        vh.tvimage.setImageResource(k.getFoto());
        vh.tvimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvInteface.onitemclick(friendlistLists.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.friendlistLists.size();
    }
    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String encodedImage = strings[0];
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}

