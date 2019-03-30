package com.computalimpo.plantorium.POJO;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "category_table")

public class CategoryPOJO {
    @PrimaryKey
    private int id;

    @ColumnInfo(name="category_name")
    private String name;

    @ColumnInfo(name="category_water")
    private boolean water;

    @ColumnInfo(name="category_prune")
    private boolean prune;

    @ColumnInfo(name="category_ill")
    private boolean ill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public boolean isPrune() {
        return prune;
    }

    public void setPrune(boolean prune) {
        this.prune = prune;
    }

    public boolean isIll() {
        return ill;
    }

    public void setIll(boolean ill) {
        this.ill = ill;
    }


}