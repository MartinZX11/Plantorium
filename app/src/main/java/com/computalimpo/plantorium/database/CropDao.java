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

    @Delete
    void deleteCrop(CropPOJO cropPOJO);


}
