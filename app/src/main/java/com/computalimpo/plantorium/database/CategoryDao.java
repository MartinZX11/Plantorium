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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(CategoryPOJO categoryPOJO);

    @Delete
    void deleteCategpory(CategoryPOJO categoryPOJO);
}
