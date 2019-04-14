package com.computalimpo.plantorium;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.computalimpo.plantorium.fragments.DatePickerFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    TextView dateTextView;
    Spinner spinner;
    Spinner spinner_type;
    EditText taskName;
    Date date;
    String category;
    EditText infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ImageButton datePicker = findViewById(R.id.date_picker_button);
        Button cancelButton = findViewById(R.id.cancel_add_task_button);
        Button addButton = findViewById(R.id.accept_add_task_button);



        spinner = findViewById(R.id.crop_spinner);
        spinner_type = findViewById(R.id.subcategory_spinner);
        dateTextView = findViewById(R.id.date_textview);
        infoText = findViewById(R.id.infoText);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });



        loadSpinnerData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                List<CategoryPOJO> categories =  CropsDatabase.getInstance(getApplicationContext()).categoryDao().getCategories();

                //List<String> names = CropsDatabase.getInstance(getApplicationContext()).categoryDao().getCategoryNames();
                ArrayAdapter<CategoryPOJO> dataAdapter = new ArrayAdapter<CategoryPOJO>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);


            }
        }).start();
    }

    private void addTask(){

        final CategoryPOJO c = (CategoryPOJO) spinner.getSelectedItem();
        final String temptasktype = spinner_type.getSelectedItem().toString();
        final String info = c.getName() + " [" + spinner_type.getSelectedItem().toString() + "] " + infoText.getText().toString();
        String dateDefault = getResources().getString(R.string.date_default);
        if(c.getTastkTypes(getApplicationContext()).contains(temptasktype) && !dateTextView.getText().toString().equals(dateDefault)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CropsDatabase.getInstance(getApplicationContext()).taskDao().addTask(new TaskPOJO(c.getId(),dateTextView.getText().toString(), spinner_type.getSelectedItem().toString() ,info));

                }
            }).start();
            finish();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "";
            if(dateTextView.getText().toString().equals(dateDefault)) text = getResources().getString(R.string.pickDate);
            if(!c.getTastkTypes(context).contains(temptasktype)) {
                if (!text.equals("")) {
                    text = text + "\n" + getResources().getString(R.string.typeNotValid);
                } else {
                    text = getResources().getString(R.string.typeNotValid);
                }
            }
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


        }

    }






}
