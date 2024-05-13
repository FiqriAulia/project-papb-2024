package com.example.pamproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pamproject.databinding.ActivityLihatfototemanBinding;

public class lihatfototeman extends AppCompatActivity {

    private ActivityLihatfototemanBinding binding; // Menggunakan binding
    private ImageButton btnimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLihatfototemanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Menggunakan root dari binding sebagai contentView

        Intent intent = getIntent();
        String imgBase64 = intent.getStringExtra("imgId");
        if (imgBase64 != null) {
            byte[] imgBytes = Base64.decode(imgBase64, Base64.DEFAULT); // Konversi string Base64 menjadi byte array
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length); // Ubah byte array menjadi gambar bitmap
            binding.imageView4.setImageBitmap(bitmap); // Tampilkan gambar bitmap di ImageView
        }


        binding.tvnama.setText(intent.getStringExtra("namaId"));
        binding.tvdeskripsi.setText(intent.getStringExtra("deskripsiId"));

//        String imgId = intent.getStringExtra("imgId");
//        Glide.with(this)
//                .load(Base64.decode(imgId, Base64.DEFAULT))
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
//                .into(binding.imageView4);

        btnimg = binding.imageButton2;
        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lihatfototeman.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
