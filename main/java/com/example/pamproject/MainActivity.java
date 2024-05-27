package com.example.pamproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pamproject.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<friendlist> data;
    private RecyclerView rvfoto;
    private ActivityMainBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDatabase = FirebaseDatabase.getInstance("https://pamproject-f9290-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();

        data = new ArrayList<>();
        rvfoto = binding.rvfoto;
        rvfoto.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.HORIZONTAL, false));

        fetchData();
    }

    private void fetchData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    friendlist friend = snapshot.getValue(friendlist.class);
                    if (friend != null) {
                        data.add(friend);
                    }
                }

                RvAdapter rvAdapter = new RvAdapter(MainActivity.this, data, new RvInteface() {
                    @Override
                    public void onItemClick(friendlist friendlist) {
                        LihatFotoTemanFragment fragment = new LihatFotoTemanFragment();
                        Bundle args = new Bundle();
                        args.putString("imgId", friendlist.getFotoid());
                        args.putString("namaId", friendlist.getNama());
                        args.putString("deskripsiId", friendlist.getDeskripsi());
                        fragment.setArguments(args);

                        replaceFragment(fragment);
                    }
                });
                binding.rvfoto.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
