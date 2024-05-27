//package com.example.edit;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProfileDao implements ProfilDAO {
//    private SQLiteDatabase database;
//    private SQLiteOpenHelper dbHelper;
//    private String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DOB, COLUMN_IMAGE_URI};
//
//    public static final String TABLE_PROFILES = "profiles";
//    public static final String COLUMN_ID = "_id";
//    public static final String COLUMN_NAME = "name";
//    public static final String COLUMN_DESCRIPTION = "description";
//    public static final String COLUMN_DOB = "dob";
//    public static final String COLUMN_IMAGE_URI = "image_uri";
//
//    private static final String DATABASE_NAME = "profiles.db";
//    private static final int DATABASE_VERSION = 1;
//
//    private static final String DATABASE_CREATE = "create table "
//            + TABLE_PROFILES + "(" + COLUMN_ID
//            + " integer primary key autoincrement, " + COLUMN_NAME
//            + " text not null, " + COLUMN_DESCRIPTION
//            + " text not null, " + COLUMN_DOB
//            + " text not null, " + COLUMN_IMAGE_URI
//            + " text);";
//
//    public ProfileDao(Context context) {
//        dbHelper = new ProfileDBHelper(context);
//    }
//
//    public void open() throws SQLException {
//        database = dbHelper.getWritableDatabase();
//    }
//
//    public void close() {
//        dbHelper.close();
//    }
//
//    public Profile createProfile(String name, String description, String dob, String imageUri) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, name);
//        values.put(COLUMN_DESCRIPTION, description);
//        values.put(COLUMN_DOB, dob);
//        values.put(COLUMN_IMAGE_URI, imageUri);
//        long insertId = database.insert(TABLE_PROFILES, null, values);
//        Cursor cursor = database.query(TABLE_PROFILES, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);
//        cursor.moveToFirst();
//        Profile newProfile = cursorToProfile(cursor);
//        cursor.close();
//        return newProfile;
//    }
//
//    public Profile getProfileById(long id) {
//        Cursor cursor = database.query(TABLE_PROFILES, allColumns, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            Profile profile = cursorToProfile(cursor);
//            cursor.close();
//            return profile;
//        }
//        return null;
//    }
//
//    public Profile getFirstProfile() {
//        Profile profile = null;
//        Cursor cursor = database.query(TABLE_PROFILES,
//                allColumns, null, null, null, null, COLUMN_ID + " ASC", "1");
//
//        if (cursor != null && cursor.moveToFirst()) {
//            profile = cursorToProfile(cursor);
//            cursor.close();
//        }
//        return profile;
//    }
//
//    public void updateProfile(Profile profile) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, profile.getName());
//        values.put(COLUMN_DESCRIPTION, profile.getDescription());
//        values.put(COLUMN_DOB, profile.getDob());
//        values.put(COLUMN_IMAGE_URI, profile.getImageUri());
//
//        database.update(TABLE_PROFILES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(profile.getId())});
//    }
//
//    public List<Profile> getAllProfiles() {
//        List<Profile> profiles = new ArrayList<>();
//
//        Cursor cursor = database.query(TABLE_PROFILES,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Profile profile = cursorToProfile(cursor);
//            profiles.add(profile);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return profiles;
//    }
//
//    public void deleteProfile(Profile profile) {
//        long id = profile.getId();
//        database.delete(TABLE_PROFILES, COLUMN_ID + " = " + id, null);
//    }
//
//    private Profile cursorToProfile(Cursor cursor) {
//        Profile profile = new Profile();
//        profile.setId(cursor.getLong(0));
//        profile.setName(cursor.getString(1));
//        profile.setDescription(cursor.getString(2));
//        profile.setDob(cursor.getString(3));
//        profile.setImageUri(cursor.getString(4));
//        return profile;
//    }
//
//    private static class ProfileDBHelper extends SQLiteOpenHelper {
//        ProfileDBHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(DATABASE_CREATE);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
//            onCreate(db);
//        }
//    }
//}