package com.computalimpo.plantorium.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.computalimpo.plantorium.helper.TaskType;

@Entity(tableName = "task_table",indices ={@Index("task_category")},
        foreignKeys = @ForeignKey(entity = CategoryPOJO.class, parentColumns = "id", childColumns = "task_category"))
public class TaskPOJO {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="task_category")
    private int category;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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


    public TaskPOJO(int category, String date, String type, String taskText) {

        this.category = category;
        String[] parts = date.split("/");
        this.date = parts[2] + "/" + parts[1] + "/" + parts[0];
        this.type = type;
        this.taskText = taskText;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getInfo() {
        String res = "Category :" + category + " must " + type + " on " + date;
        return res;
    }

    public String getInfoInCategory() {
        String res = getNormalDate() + " : " + type;
        return res;
    }

    public String getNormalDate(){
        String[] parts = date.split("/");
        return parts[2] + "/" + parts[1] + "/" + parts[0];
    }

    public String getTextForFragment(){
        return taskText;
    }


}
