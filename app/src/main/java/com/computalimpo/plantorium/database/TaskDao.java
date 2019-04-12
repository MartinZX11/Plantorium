package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;

import java.util.List;

@Dao
public interface TaskDao {


    @Query("SELECT * FROM task_table")
    List<TaskPOJO> getTask();
    @Query("SELECT * FROM task_table WHERE task_date = :date")
    List<TaskPOJO> getTasksWithDate(String date);

    @Query("SELECT * FROM task_table WHERE task_category = :c")
    List<TaskPOJO> getTasksCategory(int c);

    @Query("DELETE FROM task_table")
    void deleteAllTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(TaskPOJO taskPOJO);

    @Delete
    void deleteTask(TaskPOJO taskPOJO);

}
