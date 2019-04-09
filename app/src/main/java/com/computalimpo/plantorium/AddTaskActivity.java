package com.computalimpo.plantorium;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.computalimpo.plantorium.fragments.DatePickerFragment;

import java.util.Date;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    TextView dateTextView;
    Spinner spinner;
    EditText taskName;
    Date date;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ImageButton datePicker = findViewById(R.id.date_picker_button);
        Button cancelButton = findViewById(R.id.cancel_add_task_button);
        spinner = findViewById(R.id.crop_spinner);
        dateTextView = findViewById(R.id.date_textview);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        loadSpinnerData();
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                dateTextView.setText(selectedDate);
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void loadSpinnerData(){



        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> names = CropsDatabase.getInstance(getApplicationContext()).categoryDao().getCategoryNames();
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, names);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

            }
        }).start();

    }

    private void addTask(View v){

        String s = spinner.getSelectedItem().toString();

        //ToDo Obtener la categoria elegida
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {

                CropsDatabase.getInstance(getApplicationContext()).taskDao().addTask(new TaskPOJO());

            }
        }).start();
        finish();*/


    }






}
