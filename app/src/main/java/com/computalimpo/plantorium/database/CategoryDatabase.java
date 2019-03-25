package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.computalimpo.plantorium.POJO.CategoryPOJO;

@Database(entities = {CategoryPOJO.class}, version = 1,exportSchema = false)

public abstract class CategoryDatabase extends RoomDatabase {

    private static CategoryDatabase categoryDatabase;

    public synchronized static CategoryDatabase getInstance(Context context) {
        if (categoryDatabase == null) {

            categoryDatabase = Room.databaseBuilder(context, CategoryDatabase.class, "quotation_database").build();
        }
        return categoryDatabase;
    }

    public abstract CategoryDao categoryDao();

}


