package com.example.tambahteman.friendrequest.progress5;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tambahteman.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FriendAdapter adapter;
    private List<Friend> friendList;
    private EditText searchEditText;
    private ImageButton notifButton;
    private FriendsRequestFragment friendsRequestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpro5);

        recyclerView = findViewById(R.id.recycler_view);
        searchEditText = findViewById(R.id.searchbar);
        notifButton = findViewById(R.id.notif);

        friendList = new ArrayList<>();
        friendList.add(new Friend("Afianada"));
        friendList.add(new Friend("Arvin"));
        friendList.add(new Friend("Lidia"));
        friendList.add(new Friend("Fiqri"));

        adapter = new FriendAdapter(this, friendList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        friendsRequestFragment = new FriendsRequestFragment();
        getSupportFragmentManager().beginTransaction()
                .hide(friendsRequestFragment)
                .commit();

        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Notif button clicked");
                showFriendsRequestFragment();
            }
        });

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim();
                performSearch(searchText);
            }
        });
    }

    private void performSearch(String searchText) {
        List<Friend> filteredList = new ArrayList<>();
        for (Friend friend : friendList) {
            if (friend.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(friend);
            }
        }
        adapter.setFilteredList(filteredList);
    }

    private void showFriendsRequestFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(friendsRequestFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}