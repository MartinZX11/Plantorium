package com.computalimpo.plantorium.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.computalimpo.plantorium.helper.TaskType;

@Entity(tableName = "task_table",indices ={@Index("task_crop")},
        foreignKeys = @ForeignKey(entity = CropPOJO.class, parentColumns = "id", childColumns = "task_crop"))
public class TaskPOJO {
    @PrimaryKey
    private int id;
    @ColumnInfo(name="task_crop")
    private int crop;
    @ColumnInfo(name="task_date")
    private String date;
    @ColumnInfo(name="task_type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrop() {
        return crop;
    }

    public void setCrop(int crop) {
        this.crop = crop;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String taskText;
    private String priority;

    public TaskPOJO(int id, int crop, String date, String type, String taskText, String priority) {
        this.id = id;
        this.crop = crop;
        this.date = date;
        this.type = type;
        this.taskText = taskText;
        this.priority = priority;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
