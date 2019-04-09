package com.computalimpo.plantorium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.computalimpo.plantorium.fragments.CropsFragment;

public class AddCropActivity extends AppCompatActivity {



    private EditText categoryName;
    private EditText categoryLocation;
    private CheckBox water;
    private CheckBox ill;
    private CheckBox prune;
    private EditText categoryNumber;


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
        categoryLocation = (EditText) findViewById(R.id.categoryLocation);
        categoryNumber = (EditText) findViewById(R.id.categoryNumber);

        new Thread(new Runnable() {
            @Override
            public void run() {

                CropsDatabase.getInstance(getApplicationContext()).categoryDao().addCategory(new CategoryPOJO(categoryName.getText().toString(), categoryLocation.getText().toString(),water.isChecked(), prune.isChecked(),ill.isChecked(), Integer.parseInt(categoryNumber.getText().toString())));

            }
        }).start();
        finish();
    }

    public void buttonCancel(View v){

        finish();
    }


}
