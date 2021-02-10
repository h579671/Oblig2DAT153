package com.example.navdrawerdemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CatDAO {
    @Query("SELECT * FROM catobject")
    List<CatObject> getAll();

    @Query("SELECT * FROM catobject WHERE uid IN (:catobjectIds)")
    List<CatObject> loadAllByIds(int[] catobjectIds);

    @Query("SELECT * FROM catobject WHERE cat_name LIKE :cat AND " +
            "image_name LIKE :image LIMIT 1")
    CatObject findByName(String cat, byte[] image);

    @Insert
    void insertAll(CatObject... catObjects);

    @Delete
    void delete(CatObject catObject);
}
