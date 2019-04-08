package com.computalimpo.plantorium.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "crop_table")
public class CropPOJO {

   @PrimaryKey(autoGenerate = true)
    private int id;




    public CropPOJO(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
