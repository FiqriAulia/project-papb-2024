package com.example.tambahtemanversi2.addfriend;

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
import com.example.tambahtemanversi2.friendrequest.FriendRequestActivityPro6;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFriendActivityPro6 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AddFriendAdapterPro6 adapter;
    private List<AddFriendPro6> friendList;
    private EditText searchEditText;
    private ImageButton notifButton;
    private DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friendpro6);

        recyclerView = findViewById(R.id.recycler_view);
        searchEditText = findViewById(R.id.searchbar);
        notifButton = findViewById(R.id.notif);

        friendList = new ArrayList<>();
        adapter = new AddFriendAdapterPro6(this, friendList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userDatabase = FirebaseDatabase.getInstance().getReference().child("user");

        loadFriendData();

        notifButton.setOnClickListener(v -> {
            Intent friendRequestIntent = new Intent(AddFriendActivityPro6.this, FriendRequestActivityPro6.class);
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
    }

    private void performSearch(String searchText) {
        userDatabase.orderByChild("keywords/" + searchText).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<AddFriendPro6> searchResults = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AddFriendPro6 friend = snapshot.getValue(AddFriendPro6.class);
                            if (friend != null) {
                                searchResults.add(friend);
                            }
                        }
                        adapter.setFilteredList(searchResults);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase Search", "Error getting data", databaseError.toException());
                    }
                });
    }

    private void loadFriendData() {
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AddFriendPro6 friend = snapshot.getValue(AddFriendPro6.class);
                    if (friend != null) {
                        friendList.add(friend);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Load", "Error getting data", databaseError.toException());
            }
        });
    }

    private void insertDummyData() {
        String profileImageUrl = "https://firebasestorage.googleapis.com/v0/b/tambahteman2-7fe67.appspot.com/o/Driver.png?alt=media&token=3ee6c645-a115-45ff-a977-7db19b1e0274";
        Map<String, Boolean> keywords = new HashMap<>();
        keywords.put("D", true);
        keywords.put("Dr", true);
        keywords.put("Dri", true);
        keywords.put("Driv", true);
        keywords.put("Drive", true);
        keywords.put("Driver", true);

        AddFriendPro6 friend = new AddFriendPro6("6", "Driver", profileImageUrl, keywords);

        userDatabase.child(friend.getId()).setValue(friend)
                .addOnSuccessListener(aVoid -> loadFriendData())
                .addOnFailureListener(e -> Log.e("Firebase Insert", "Error adding document", e));
    }
}
