package com.example.edit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "MainActivity";
    private ImageView selectImage, ivProfile;
    private EditText etNama, etDesc, etDOB;
    private TextView tvNama, tvDesc, tvDOB;
    private Button btSave, btEdit;
    private ConstraintLayout Edit, View;
    private DatabaseReference databaseReference;
    private Uri selectedImageUri;
    private String profileId = "Profile"; // Unique identifier for the profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        selectImage = findViewById(R.id.ivUpdate);
        ivProfile = findViewById(R.id.ivProfile);
        etNama = findViewById(R.id.etNama);
        etDesc = findViewById(R.id.etDesc);
        etDOB = findViewById(R.id.etDOBU);
        btEdit = findViewById(R.id.btEdit);
        btSave = findViewById(R.id.btSave);
        Edit = findViewById(R.id.Edit);
        View = findViewById(R.id.View);
        tvNama = findViewById(R.id.tvNama);
        tvDesc = findViewById(R.id.tvDesc);
        tvDOB = findViewById(R.id.tvDOB);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance("https://edit-e896a-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("profiles");

        // Load the profile data from Firebase
        loadProfile();

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (View.getVisibility() == View.VISIBLE) {
                    View.setVisibility(View.INVISIBLE);
                    Edit.setVisibility(View.VISIBLE);
                } else {
                    Edit.setVisibility(View.VISIBLE);
                }
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void loadProfile() {
        databaseReference.child(profileId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Profile profile = snapshot.getValue(Profile.class);
                if (profile != null) {
                    etNama.setText(profile.getName());
                    etDesc.setText(profile.getDescription());
                    etDOB.setText(profile.getDob());
                    if (profile.getImageUri() != null) {
                        selectedImageUri = Uri.parse(profile.getImageUri());
                        try {
                            Glide.with(MainActivity.this)
                                    .load(selectedImageUri)
                                    .into(selectImage);
                            Glide.with(MainActivity.this)
                                    .load(selectedImageUri)
                                    .into(ivProfile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    updateUI(profile);
                } else {
                    Log.d(TAG, "Profile data not found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
            }
        });
    }

    private void saveProfile() {
        String name = etNama.getText().toString();
        String desc = etDesc.getText().toString();
        String dob = etDOB.getText().toString();
        String imageUri = selectedImageUri != null ? selectedImageUri.toString() : null;

        Profile profile = new Profile(name, desc, dob, imageUri);

        databaseReference.child(profileId).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateUI(profile);
                } else {
                    Log.e(TAG, "Failed to save profile: " + task.getException().getMessage());
                }
            }
        });

        Edit.setVisibility(View.INVISIBLE);
        View.setVisibility(View.VISIBLE);
    }

    private void updateUI(Profile profile) {
        tvNama.setText(profile.getName());
        tvDesc.setText(profile.getDescription());
        tvDOB.setText(profile.getDob());
        if (profile.getImageUri() != null) {
            Uri uri = Uri.parse(profile.getImageUri());
            try {
                Glide.with(this)
                        .load(uri)
                        .into(selectImage);
                Glide.with(this)
                        .load(uri)
                        .into(ivProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Glide.with(this)
                        .load(selectedImageUri)
                        .into(selectImage);
                Glide.with(this)
                        .load(selectedImageUri)
                        .into(ivProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
