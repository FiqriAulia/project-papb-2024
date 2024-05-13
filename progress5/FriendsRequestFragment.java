package com.example.tambahteman.friendrequest.progress5;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tambahteman.R;
import com.example.tambahteman.friendrequest.progress5.MainActivity;
import com.example.tambahteman.friendrequest.FriendRequest;
import com.example.tambahteman.friendrequest.FriendRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendsRequestFragment extends Fragment {
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private List<FriendRequest> friendRequestList;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_requestpro5, container, false);

        ImageButton backButton = rootView.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().getSupportFragmentManager().beginTransaction().remove(FriendsRequestFragment.this).commit();
            }
        });

        recyclerView = rootView.findViewById(R.id.recycler_view);

        friendRequestList = new ArrayList<>();
        friendRequestList.add(new FriendRequest("Narumi"));
        friendRequestList.add(new FriendRequest("Hirotaka"));
        friendRequestList.add(new FriendRequest("Luo Yi"));
        friendRequestList.add(new FriendRequest("Cici"));

        adapter = new FriendRequestAdapter(getActivity(), friendRequestList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchEditText = rootView.findViewById(R.id.searchbar);

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

        return rootView;
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
