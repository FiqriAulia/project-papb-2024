package com.example.pamproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.pamproject.databinding.ActivityLihatfototemanBinding;

public class LihatFotoTemanFragment extends Fragment {

    private ActivityLihatfototemanBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityLihatfototemanBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        Bundle args = getArguments();
        if (args != null) {
            String imgId = args.getString("imgId");
            String namaId = args.getString("namaId");
            String deskripsiId = args.getString("deskripsiId");

            if (imgId != null) {
                byte[] imgBytes = Base64.decode(imgId, Base64.DEFAULT); // Konversi string Base64 menjadi byte array
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length); // Ubah byte array menjadi gambar bitmap
                binding.imageView4.setImageBitmap(bitmap); // Tampilkan gambar bitmap di ImageView
            }
            binding.tvnama.setText(namaId);
            binding.tvdeskripsi.setText(deskripsiId);
            binding.imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireFragmentManager().popBackStack();                }
            });
        }

        return rootView;
    }

}



