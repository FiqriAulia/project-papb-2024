
package com.example.pam_projek;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ImageDao {

    @Insert
    void insert(Image image);
}

