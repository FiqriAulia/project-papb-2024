package com.example.pamproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.pamproject.databinding.ActivityLihatfototemanBinding;

public class LihatFotoTemanFragment extends Fragment {

    private ActivityLihatfototemanBinding binding; // Menggunakan binding
    private ImageButton btnimg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityLihatfototemanBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        Intent intent = getActivity().getIntent();
        String imgBase64 = intent.getStringExtra("imgId");
        if (imgBase64 != null) {
            byte[] imgBytes = Base64.decode(imgBase64, Base64.DEFAULT); // Konversi string Base64 menjadi byte array
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length); // Ubah byte array menjadi gambar bitmap
            binding.imageView4.setImageBitmap(bitmap); // Tampilkan gambar bitmap di ImageView
        }

        binding.tvnama.setText(intent.getStringExtra("namaId"));
        binding.tvdeskripsi.setText(intent.getStringExtra("deskripsiId"));

        btnimg = binding.imageButton2;
        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }
}
