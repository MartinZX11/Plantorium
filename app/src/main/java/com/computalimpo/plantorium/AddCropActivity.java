package com.computalimpo.plantorium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;

public class AddCropActivity extends AppCompatActivity {



    private EditText categoryName;
    private CheckBox water;
    private CheckBox ill;
    private CheckBox prune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);


    }


    public void addCategory(View view){

        categoryName = (EditText) findViewById(R.id.categoryName);
        water = (CheckBox) findViewById(R.id.checkWater);
        ill = (CheckBox) findViewById(R.id.checkIll);
        prune = (CheckBox) findViewById(R.id.checkPrune);

        new Thread(new Runnable() {
            @Override
            public void run() {

                CropsDatabase.getInstance(getApplicationContext()).categoryDao().addCategory(new CategoryPOJO(categoryName.getText().toString(),water.isChecked(), prune.isChecked(),ill.isChecked()));

            }
        }).start();
        finish();
    }


}
