package com.computalimpo.plantorium.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.computalimpo.plantorium.POJO.CategoryPOJO;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category_table")
    List<CategoryPOJO> getCategories();

    @Query("SELECT * FROM category_table WHERE id = :iden ")
    CategoryPOJO getCategoryById(int iden);

    @Query("Select COUNT(id) FROM category_table WHERE id = :iden  ")
    int getNum(int iden);

    @Query("SELECT category_name FROM category_table")
    List<String> getCategoryNames();

    @Query("SELECT category_location FROM category_table")
    List<String> getLocations();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(CategoryPOJO categoryPOJO);

    @Delete
    void deleteCategory(CategoryPOJO categoryPOJO);
}
