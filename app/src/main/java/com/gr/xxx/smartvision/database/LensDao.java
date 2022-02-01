package com.gr.xxx.smartvision.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LensDao {

    @Insert
    void insertLens(Lens lens);

    @Query("SELECT * FROM lens")
    LiveData<List<Lens>> getAll();

    @Query("DELETE FROM lens")
    void deleteAll();

    @Delete
    void deleteLens(Lens lens_id);

    @Query("SELECT * FROM lens WHERE id =:id")
    LiveData<Lens> getLens(int id);
}
