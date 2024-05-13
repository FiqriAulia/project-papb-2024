package com.example.tambahteman.friendrequest.beforefragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tambahteman.R;
import com.example.tambahteman.friendrequest.progress5.Friend;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private List<Friend> friendList;
    private Context context;
    private List<Friend> filteredList;

    public FriendAdapter(Context context, List<Friend> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public Button buttonAdd;

        public FriendViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            buttonAdd = itemView.findViewById(R.id.button_add);
        }
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friendpro5, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        Friend friend = friendList.get(position);
        holder.textViewName.setText(friend.getName());

        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFriendRequest(friend);

                holder.buttonAdd.setText("Waiting");
                holder.buttonAdd.setTextColor(Color.WHITE);

                holder.buttonAdd.setEnabled(false);
            }
            private void sendFriendRequest(Friend friend) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : 0;
    }

    public void setFilteredList(List<Friend> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }
}
