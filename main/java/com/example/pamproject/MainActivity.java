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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<friendlist> data;
//    private List<Friend> friendList;
    private RecyclerView rvfoto;
    private ActivityMainBinding binding;
    private FriendDAO friendDAO;
    private RvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.data = new ArrayList<>();
        friendDAO = new FriendDAO(this);
//        addInitialFriends();
//        if (friendDAO.getAllFriends().isEmpty()) {
//            addInitialFriends();
//        }

        rvfoto = binding.rvfoto;
        rvfoto.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.HORIZONTAL, false));
        fetchData();

    }


    private void fetchData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<friendlist>> call = apiService.getFriendList();

        call.enqueue(new Callback<List<friendlist>>() {
            @Override
            public void onResponse(Call<List<friendlist>> call, Response<List<friendlist>> response) {
                if (response.isSuccessful()) {
                    data = response.body();
//                    Log.w("data", data.get());
                    if (data != null) {
                        RvAdapter rvAdapter = new RvAdapter(MainActivity.this, data, new RvInteface() {
                            @Override
                            public void onitemclick(friendlist friend) {
                                LihatFotoTemanFragment fragment = new LihatFotoTemanFragment();
                                Bundle args = new Bundle();
                                args.putString("imgId", friend.getFoto());
                                args.putString("namaId", friend.getNama());
                                args.putString("deskripsiId", friend.getDeskripsi());
                                fragment.setArguments(args);

                                replaceFragment(fragment);
                            }
                        });
                        binding.rvfoto.setAdapter(rvAdapter);
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

//private void fetchData() {
//    friendList = friendDAO.getAllFriends();
//    if (!friendList.isEmpty()) {
//        rvAdapter = new RvAdapter(this, friendList, new RvInteface() {
//            @Override
//            public void onItemClick(Friend friend) {
//                Intent intent = new Intent(MainActivity.this, lihatfototeman.class);
//                intent.putExtra("imgId", friend.getPhoto());
//                intent.putExtra("namaId", friend.getName());
//                intent.putExtra("deskripsiId", friend.getDescription());
//                startActivity(intent);
//            }
//        });
//        binding.rvfoto.setAdapter(rvAdapter);
//    } else {
//        Toast.makeText(this, "No friends available", Toast.LENGTH_SHORT).show();
//    }
//    }
//private void fetchData() {
//    friendList = friendDAO.getAllFriends();
//    if (!friendList.isEmpty()) {
//        rvAdapter = new RvAdapter(this, friendList, new RvInteface() {
//            @Override
//            public void onItemClick(Friend friend) {
//                binding.rvfoto.setVisibility(View.GONE);
//                binding.imageButtonteman.setVisibility(View.GONE);
//                binding.imageButtonfoto.setVisibility(View.GONE);
//                binding.imageButtonmap.setVisibility(View.GONE);
//                // Memindahkan ke fragment LihatFotoTemanFragment
//                LihatFotoTemanFragment fragment = new LihatFotoTemanFragment();
//                Bundle args = new Bundle();
//                args.putString("imgId", friend.getPhoto());
//                args.putString("namaId", friend.getName());
//                args.putString("deskripsiId", friend.getDescription());
//                fragment.setArguments(args);
//
//                replaceFragment(fragment);
//            }
//        });
//        binding.rvfoto.setAdapter(rvAdapter);
//    } else {
//        Toast.makeText(this, "No friends available", Toast.LENGTH_SHORT).show();
//    }
//}
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment); // R.id.fragment_container adalah id dari container layout di activity Anda
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
