package com.example.tambahtemanversi2.addfriend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tambahtemanversi2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddFriendAdapterPro6 extends RecyclerView.Adapter<AddFriendAdapterPro6.FriendViewHolder> {

    private List<AddFriendPro6> friendList;
    private Context context;
    private DatabaseReference userDatabase;
    private DatabaseReference alreadyFriendDatabase;

    public AddFriendAdapterPro6(Context context, List<AddFriendPro6> friendList) {
        this.context = context;
        this.friendList = friendList;
        userDatabase = FirebaseDatabase.getInstance().getReference().child("user");
        alreadyFriendDatabase = FirebaseDatabase.getInstance().getReference().child("RequestToUser");
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewProfile;
        public ImageButton buttonOtherOptions;
        public Button buttonAdd;

        public FriendViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageViewProfile = itemView.findViewById(R.id.image_view_profile);
            buttonOtherOptions = itemView.findViewById(R.id.button_other_options);
            buttonAdd = itemView.findViewById(R.id.button_add);
        }
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addfriendpro6, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        AddFriendPro6 friend = friendList.get(position);
        holder.textViewName.setText(friend.getName());

        Glide.with(context)
                .load(friend.getProfileImageUrl())
                .placeholder(R.drawable.vectorimg)
                .error(R.drawable.waiting)
                .into(holder.imageViewProfile);

        holder.buttonAdd.setOnClickListener(v -> {
            removeFriend(position);
            addUserToAlreadyFriend(friend);
        });

        holder.buttonOtherOptions.setOnClickListener(v -> {
            showOtherOptionsDialog(friend);
        });
    }

    private void removeFriend(int position) {
        if (position >= 0 && position < friendList.size()) {
            AddFriendPro6 friend = friendList.get(position);
            userDatabase.child(friend.getId()).removeValue()
                    .addOnSuccessListener(aVoid -> Log.d("Database Remove", "DocumentSnapshot removed with ID: " + friend.getId()))
                    .addOnFailureListener(e -> Log.w("Database Remove", "Error removing document", e));
            friendList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private void addUserToAlreadyFriend(AddFriendPro6 friend) {
        alreadyFriendDatabase.child(friend.getId()).setValue(friend)
                .addOnSuccessListener(aVoid -> Log.d("Database Insert", "DocumentSnapshot added with ID: " + friend.getId()))
                .addOnFailureListener(e -> Log.w("Database Insert", "Error adding document", e));
    }

    private void showOtherOptionsDialog(AddFriendPro6 friend) {
        // Implement dialog to show other options if needed
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void setFilteredList(List<AddFriendPro6> filteredList) {
        this.friendList = filteredList;
        notifyDataSetChanged();
    }
}
