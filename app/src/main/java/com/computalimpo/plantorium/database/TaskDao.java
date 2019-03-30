package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.computalimpo.plantorium.POJO.TaskPOJO;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(TaskPOJO taskPOJO);
    @Query("SELECT * FROM task_table")
    List<TaskPOJO> getTask();
    @Query("SELECT * FROM task_table WHERE task_date = :date")
    List<TaskPOJO> getTasksWithDate(String date);
    @Query("DELETE FROM task_table")
    void deleteAllTask();

    @Delete
    void deleteTask(TaskPOJO taskPOJO);

}
