package com.computalimpo.plantorium.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "crop_table",indices ={@Index("crop_category")},
        foreignKeys = @ForeignKey(entity = CategoryPOJO.class, parentColumns = "id", childColumns = "crop_category"))
public class CropPOJO {
   @PrimaryKey(autoGenerate = true)
    private int id;
   @ColumnInfo(name="crop_sector")
    private int sector;
    @ColumnInfo(name="crop_row")
    private int row;
    @ColumnInfo(name="crop_pos_in_row")
    private int pos_in_row;

    @ColumnInfo(name="crop_category")
    private int category;

    @ColumnInfo(name="crop_water")
    private int water;

    public CropPOJO(){

    }

    public CropPOJO(int sector, int row, int pos_in_row, int category, int water, int prune, int ill) {
        this.sector = sector;
        this.row = row;
        this.pos_in_row = pos_in_row;
        this.category = category;
        this.water = water;
        this.prune = prune;
        this.ill = ill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPos_in_row() {
        return pos_in_row;
    }

    public void setPos_in_row(int pos_in_row) {
        this.pos_in_row = pos_in_row;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getPrune() {
        return prune;
    }

    public void setPrune(int prune) {
        this.prune = prune;
    }

    public int getIll() {
        return ill;
    }

    public void setIll(int ill) {
        this.ill = ill;
    }

    @ColumnInfo(name="crop_prune")
    private int prune;

    @ColumnInfo(name="crop_ill")
    private int ill;

}
