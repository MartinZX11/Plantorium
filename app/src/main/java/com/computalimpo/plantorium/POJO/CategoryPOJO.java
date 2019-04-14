package com.computalimpo.plantorium.POJO;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Point;
import com.google.android.gms.maps.model.LatLng;
import android.content.Context;
import android.content.res.Resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.computalimpo.plantorium.R;

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

    @ColumnInfo(name="category_excess")
    private boolean excess;

    @ColumnInfo(name="category_ill")
    private boolean ill;

    @ColumnInfo(name="category_harvest")
    private boolean harvest;

    @ColumnInfo(name="category_lack")
    private boolean lack;

    @ColumnInfo(name="category_herbicide")
    private boolean herbicide;

    @ColumnInfo(name="category_number")
    private int number;

    @ColumnInfo(name="category_image")
    private String image;

    @ColumnInfo(name="category_latitude")
    private Double latitude;

    @ColumnInfo(name="category_longitude")
    private Double longitude;

    public CategoryPOJO(){

    }

    public boolean isExcess() {
        return excess;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }

    public boolean isLack() {
        return lack;
    }

    public void setLack(boolean lack) {
        this.lack = lack;
    }

    public boolean isHerbicide() {
        return herbicide;
    }

    public void setHerbicide(boolean herbicide) {
        this.herbicide = herbicide;
    }

    public CategoryPOJO(String name, String location, boolean water, boolean prune, boolean excess, boolean ill, boolean harvest, boolean lack, boolean herbicide, int number, String image, Double latitude, Double longitude) {
        this.name = name;
        this.location = location;
        this.water = water;
        this.prune = prune;
        this.excess = excess;
        this.ill = ill;
        this.harvest = harvest;
        this.lack = lack;
        this.herbicide = herbicide;
        this.number = number;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public boolean isHarvest() {
        return harvest;
    }

    public void setHarvest(boolean harvest) {
        this.harvest = harvest;
    }

    public int getNumber() {return number; }

    public void setNumber(int number) {this.number = number; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public  Double getLatitude() { return  latitude; }

    public  void setLatitude(Double latitude) { this.latitude = latitude; }

    public  Double getLongitude() { return  longitude; }

    public  void setLongitude(Double longitude) { this.longitude = longitude; }


    public List<String> getTastkTypes(Context context){
        List<String> res = new ArrayList<String>();
        if(water) res.add(context.getString(R.string.WATER));
        if(prune) res.add(context.getString(R.string.PRUNE));
        if(excess) res.add(context.getString(R.string.EXCESS));
        if(ill) res.add(context.getString(R.string.ILL));
        if(harvest) res.add(context.getString(R.string.HARVEST));
        if(lack) res.add(context.getString(R.string.LACK));
        if(herbicide) res.add(context.getString(R.string.HERBICIDE));
        return res;
    }

    public String getRequirements(Context context){
        String res = "";
        if(water) res += ", " + context.getString(R.string.WATER);
        if(prune) res += ", " + context.getString(R.string.PRUNE);
        if(excess) res += ", " + context.getString(R.string.EXCESS);
        if(ill) res += ", " + context.getString(R.string.ILL);
        if(harvest) res += ", " + context.getString(R.string.HARVEST);
        if(lack) res += ", " + context.getString(R.string.LACK);
        if(herbicide) res += ", " + context.getString(R.string.HERBICIDE);

        if(!res.isEmpty()) {
            res = res.substring(2);
            String firstLetter =  res.substring(0,1);
            res = res.substring(1);
            res = res.toLowerCase();
            res = firstLetter + res + ".";
        }
        return res;
    }

    @Override
    public String toString() {
        return name;
    }
}
