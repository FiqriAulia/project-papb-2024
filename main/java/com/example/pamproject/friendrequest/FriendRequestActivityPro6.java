package com.example.tambahtemanversi2.friendrequest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tambahtemanversi2.R;
import com.example.tambahtemanversi2.addfriend.AddFriendActivityPro6;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendRequestActivityPro6 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FriendRequestAdapterPro6 adapter;
    private List<FriendRequestPro6> friendList;
    private EditText searchEditText;
    private DatabaseReference friendsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_requestpro6);

        recyclerView = findViewById(R.id.recycler_view);
        searchEditText = findViewById(R.id.searchbar);
        ImageButton backButton = findViewById(R.id.back);
        friendList = new ArrayList<>();
        adapter = new FriendRequestAdapterPro6(this, friendList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        friendsDatabase = FirebaseDatabase.getInstance().getReference().child("friendrequest");

        backButton.setOnClickListener(v -> {
            Intent friendRequestIntent = new Intent(FriendRequestActivityPro6.this, AddFriendActivityPro6.class);
            startActivity(friendRequestIntent);
        });

        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && searchEditText.getText().toString().equals("Search Username")) {
                searchEditText.setText("");
            } else if (!hasFocus && searchEditText.getText().toString().isEmpty()) {
                searchEditText.setText("Search Username");
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim().toLowerCase();
                if (searchText.isEmpty()) {
                    loadFriendData();
                } else {
                    performSearch(searchText);
                }
            }
        });

        insertDummyData();
        loadFriendData();
    }

    private void performSearch(String searchText) {
        Log.d("PerformSearch", "Searching for: " + searchText);
        friendsDatabase.orderByChild("keywords/" + searchText).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("PerformSearch", "DataSnapshot: " + dataSnapshot.toString());
                        List<FriendRequestPro6> searchResults = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            try {
                                FriendRequestPro6 friend = snapshot.getValue(FriendRequestPro6.class);
                                if (friend != null) {
                                    searchResults.add(friend);
                                }
                            } catch (DatabaseException e) {
                                Log.e("PerformSearch", "Error parsing data", e);
                            }
                        }
                        adapter.setFilteredList(searchResults);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Database Search", "Error getting data: ", databaseError.toException());
                    }
                });
    }

    private void loadFriendData() {
        friendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        FriendRequestPro6 friend = snapshot.getValue(FriendRequestPro6.class);
                        if (friend != null) {
                            friendList.add(friend);
                        }
                    } catch (DatabaseException e) {
                        Log.e("Database Load", "Error parsing data", e);
                    }
                }
                showFriendData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database Load", "Error getting data: ", databaseError.toException());
            }
        });
    }

    private void showFriendData() {
        adapter.notifyDataSetChanged();
    }

    private void insertDummyData() {
        String profileImageUrl = "https://firebasestorage.googleapis.com/v0/b/tambahteman2-7fe67.appspot.com/o/hirotaka.jpeg?alt=media&token=073be67a-f769-43ef-93f6-b865b0299d0b";
        Map<String, Boolean> keywords = new HashMap<>();
        keywords.put("h", true);
        keywords.put("hi", true);
        keywords.put("hir", true);
        keywords.put("hiro", true);

        FriendRequestPro6 friend = new FriendRequestPro6("5", "Hiro", profileImageUrl, keywords);

        Log.d("Firebase Insert", "Attempting to insert: " + friend.toString());
        friendsDatabase.child(friend.getId()).setValue(friend)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase Insert", "Data successfully inserted");
                    loadFriendData();
                })
                .addOnFailureListener(e -> Log.e("Firebase Insert", "Error adding document", e));
    }
}
