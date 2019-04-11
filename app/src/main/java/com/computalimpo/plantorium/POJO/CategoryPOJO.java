package com.computalimpo.plantorium.POJO;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "category_table")

public class CategoryPOJO implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="category_name")
    private String name;

    @ColumnInfo(name="category_location")
    private String location;

    @ColumnInfo(name="category_water")
    private boolean water;

    @ColumnInfo(name="category_prune")
    private boolean prune;

    @ColumnInfo(name="category_ill")
    private boolean ill;

    @ColumnInfo(name="category_number")
    private int number;

    @ColumnInfo(name="category_image")
    private String image;

    public CategoryPOJO(){

    }

    public CategoryPOJO(String name, String location, boolean water, boolean prune, boolean ill, int number, String image) {
        this.name = name;
        this.location = location;
        this.water = water;
        this.prune = prune;
        this.ill = ill;
        this.number = number;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getNumber() {return number; }

    public void setNumber(int number) {this.number = number; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
