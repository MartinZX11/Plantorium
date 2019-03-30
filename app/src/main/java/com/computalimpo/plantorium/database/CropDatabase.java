package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.CropPOJO;

@Database(entities = {CropPOJO.class, CategoryPOJO.class}, version = 1)
public abstract class CropDatabase extends RoomDatabase {
    private static CropDatabase cropDatabase;

    public synchronized static CropDatabase getInstance(Context context) {
        if (cropDatabase == null) {

            cropDatabase = Room.databaseBuilder(context, CropDatabase.class, "crop_database").build();
        }
        return cropDatabase;
    }

    public abstract CropDao cropDao();


}
