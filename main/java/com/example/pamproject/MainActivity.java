package com.example.pamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RvInteface {

    private List<friendlist> data;
    private RecyclerView rvfoto;
    private RvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.data = new ArrayList<>();
        this.rvfoto = findViewById(R.id.rvfoto);
        this.rvAdapter = new RvAdapter(MainActivity.this, data, this);
        rvfoto.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.HORIZONTAL, false));
        rvfoto.setAdapter(rvAdapter);

        // Panggil metode untuk mengambil data menggunakan Retrofit
        fetchData();
    }

    // Metode untuk mengambil data menggunakan Retrofit
    private void fetchData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<friendlist>> call = apiService.getFriendList();

        call.enqueue(new Callback<List<friendlist>>() {
            @Override
            public void onResponse(Call<List<friendlist>> call, Response<List<friendlist>> response) {
                if (response.isSuccessful()) {
                    List<friendlist> friendList = response.body();
                    if (friendList != null) {
                        data.clear();
                        data.addAll(friendList);
                        rvAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("Retrofit", "Error: " + response.message());
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<friendlist>> call, Throwable t) {
                Log.e("Retrofit", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onitemclick(friendlist friend) {
        Intent intent = new Intent(MainActivity.this, lihatfototeman.class);
        intent.putExtra("imgId", friend.getFoto());
        intent.putExtra("namaId", friend.getNama());
        intent.putExtra("deskripsiId", friend.getDeskripsi());

        Toast.makeText(this, friend.getFoto(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
