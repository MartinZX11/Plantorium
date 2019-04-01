package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.CropPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;


@Database(entities = {CropPOJO.class, CategoryPOJO.class, TaskPOJO.class}, version = 1, exportSchema = false)
public abstract class CropsDatabase extends RoomDatabase {
    private static CropsDatabase cropsDatabase;

    public synchronized static CropsDatabase getInstance(Context context) {
        if (cropsDatabase == null) {

            cropsDatabase = Room.databaseBuilder(context, CropsDatabase.class, "crops_database").build();
        }
        return cropsDatabase;
    }

    public abstract CategoryDao categoryDao();
    public abstract CropDao cropDao();
    public abstract TaskDao taskDao();


}
