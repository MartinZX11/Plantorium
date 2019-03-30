package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.computalimpo.plantorium.POJO.CropPOJO;

import java.util.List;

@Dao
public interface CropDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCrop(CropPOJO cropPOJO);
    @Query("SELECT * FROM crop_table")
    List<CropPOJO> getCrop();
    @Query("SELECT * FROM crop_table WHERE crop_category = :category")
    List<CropPOJO> getCropOfCategory(int category);
    @Query("DELETE FROM crop_table")
    void deleteAllCrops();

    @Delete
    void deleteCrop(CropPOJO cropPOJO);
}
