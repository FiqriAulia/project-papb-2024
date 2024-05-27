package com.example.edit;
public class Profile {
    private String name;
    private String description;
    private String dob;
    private String imageUri;

    // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    public Profile() {}

    public Profile(String name, String description, String dob, String imageUri) {
        this.name = name;
        this.description = description;
        this.dob = dob;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDob() {
        return dob;
    }

    public String getImageUri() {
        return imageUri;
    }
}
