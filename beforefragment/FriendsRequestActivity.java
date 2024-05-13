package com.example.tambahteman.friendrequest.beforefragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tambahteman.R;
import com.example.tambahteman.friendrequest.FriendRequest;
import com.example.tambahteman.friendrequest.FriendRequestAdapter;
import com.example.tambahteman.friendrequest.progress5.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FriendsRequestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private List<FriendRequest> friendRequestList;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_requestpro5);

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsRequestActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });

        recyclerView = findViewById(R.id.recycler_view);

        friendRequestList = new ArrayList<>();
        friendRequestList.add(new FriendRequest("Narumi"));
        friendRequestList.add(new FriendRequest("Hirotaka"));
        friendRequestList.add(new FriendRequest("Luo Yi"));
        friendRequestList.add(new FriendRequest("Cici"));

        adapter = new FriendRequestAdapter(this, friendRequestList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchEditText = findViewById(R.id.searchbar);

        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && searchEditText.getText().toString().equals("Search Username")) {
                    searchEditText.setText("");
                } else if (!hasFocus && searchEditText.getText().toString().isEmpty()) {
                    searchEditText.setText("Search Username");
                }
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim();
                performSearch(searchText);
            }
        });
    }

    private void performSearch(String searchText) {
        List<FriendRequest> filteredList = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestList) {
            if (friendRequest.getUsername().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(friendRequest);
            }
        }
        adapter.setFilteredList(filteredList);
    }
}
