package com.example.pam_projek;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Query(value = "SELECT * FROM contacts")
    List<Contact> getAllContacts();

    @Insert
    void insert(Contact contact);

}
