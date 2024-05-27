package com.example.tambahtemanversi2.friendrequest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tambahtemanversi2.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FriendRequestAdapterPro6 extends RecyclerView.Adapter<FriendRequestAdapterPro6.FriendViewHolder> {

    private Context context;
    private List<FriendRequestPro6> friendList;
    private DatabaseReference friendRequestDatabase;
    private DatabaseReference alreadyFriendDatabase;

    public FriendRequestAdapterPro6(Context context, List<FriendRequestPro6> friendList) {
        this.context = context;
        this.friendList = friendList;
        friendRequestDatabase = FirebaseDatabase.getInstance().getReference("friendrequest");
        alreadyFriendDatabase = FirebaseDatabase.getInstance().getReference("alreadyfriend");
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_requestpro6, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        FriendRequestPro6 friend = friendList.get(position);
        holder.friendName.setText(friend.getName());

        Glide.with(context)
                .load(friend.getProfileImageUrl())
                .placeholder(R.drawable.vectorimg)
                .error(R.drawable.waiting)
                .into(holder.profileImageView);

        holder.rejectButton.setOnClickListener(v -> {
            removeFriendRequest(position);
            deleteFriendFromFirebase(friend.getId());
        });

        holder.acceptButton.setOnClickListener(v -> {
            removeFriendRequest(position);
            addFriendToAlreadyFriend(friend);
        });
    }

    private void removeFriendRequest(int position) {
        if (position >= 0 && position < friendList.size()) {
            friendList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private void deleteFriendFromFirebase(String friendId) {
        friendRequestDatabase.child(friendId).removeValue()
                .addOnSuccessListener(aVoid -> Log.d("Database Delete", "DocumentSnapshot successfully deleted"))
                .addOnFailureListener(e -> Log.w("Database Delete", "Error deleting document", e));
    }

    private void addFriendToAlreadyFriend(FriendRequestPro6 friend) {
        String friendId = friend.getId();
        if (friendId == null) {
            friendId = alreadyFriendDatabase.push().getKey();
            friend.setId(friendId);
        }
        final String finalFriendId = friendId;
        alreadyFriendDatabase.child(finalFriendId).setValue(friend)
                .addOnSuccessListener(aVoid -> Log.d("Database Insert", "DocumentSnapshot added with ID: " + finalFriendId))
                .addOnFailureListener(e -> Log.w("Database Insert", "Error adding document", e));
        // After adding to alreadyfriend, remove from friendrequest
        deleteFriendFromFirebase(finalFriendId);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void setFilteredList(List<FriendRequestPro6> filteredList) {
        this.friendList = filteredList;
        notifyDataSetChanged();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {

        TextView friendName;
        ShapeableImageView profileImageView;
        ImageButton acceptButton;
        ImageButton rejectButton;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.text_view_name);
            profileImageView = itemView.findViewById(R.id.image_view_profile);
            acceptButton = itemView.findViewById(R.id.acc);
            rejectButton = itemView.findViewById(R.id.reject);
        }
    }
}

