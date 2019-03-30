package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.CropPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;

@Database(entities = {TaskPOJO.class, CropPOJO.class, CategoryPOJO.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase taskDatabase;

    public synchronized static TaskDatabase getInstance(Context context) {
        if (taskDatabase == null) {

            taskDatabase = Room.databaseBuilder(context, TaskDatabase.class, "task_database").build();
        }
        return taskDatabase;
    }

    public abstract CropDao taskDao();


}
