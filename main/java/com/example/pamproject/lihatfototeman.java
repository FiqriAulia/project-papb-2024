package com.example.pamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class lihatfototeman extends AppCompatActivity {

    TextView nama,deskripsi;
    ImageView img;
    ImageButton btnimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lihatfototeman);

        img = findViewById(R.id.imageView4);
        nama = findViewById(R.id.tvnama);
        deskripsi = findViewById(R.id.tvdeskripsi);

        Intent intent = getIntent();
        int imgId = intent.getIntExtra("imgId", 0); // Mendapatkan id gambar dari intent
        img.setImageResource(imgId);
        this.nama.setText(intent.getStringExtra("namaId"));
        this.deskripsi.setText(intent.getStringExtra("deskripsiId"));


        btnimg = findViewById(R.id.imageButton2);
        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = new Intent(lihatfototeman.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}