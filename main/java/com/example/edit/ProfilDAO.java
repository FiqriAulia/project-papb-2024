//package com.example.edit;
//
//
//import android.content.Context;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.content.ContentValues;
//import android.database.Cursor;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.edit.Profile;
//
//public interface ProfilDAO {
//    void open() throws SQLException;
//    void close();
//    Profile createProfile(String name, String description, String dob, String imageUri);
//    Profile getProfileById(long id);
//    Profile getFirstProfile();
//    void updateProfile(Profile profile);
//    void deleteProfile(Profile profile);
//    List<Profile> getAllProfiles();
//}
