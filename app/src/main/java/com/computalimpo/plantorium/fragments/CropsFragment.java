package com.computalimpo.plantorium.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.content.Context;

import com.computalimpo.plantorium.AddCropActivity;
import com.computalimpo.plantorium.AddTaskActivity;
import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.adapters.CategoryAdapter;
import com.computalimpo.plantorium.database.CategoryDao;
import com.computalimpo.plantorium.database.CropDao;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.computalimpo.plantorium.myAsyncTasks.CategoryAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class CropsFragment extends Fragment {

    CategoryAdapter categoryAdapter;
    private int gridPosition;

    public CropsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View cropView = inflater.inflate(R.layout.fragment_crops, null);


        FloatingActionButton addCrop = cropView.findViewById(R.id.addCrop);
        CategoryAsyncTask categoryAsyncTask = new CategoryAsyncTask(this);


        categoryAdapter = new CategoryAdapter(getContext(), R.layout.crop_list_item, new ArrayList<CategoryPOJO>());
        categoryAsyncTask.execute(true);
        final GridView cropsGridView = cropView.findViewById(R.id.categoryGridView);
        cropsGridView.setAdapter(categoryAdapter);

        addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCropActivity.class);
                startActivity(intent);
            }
        });

        cropsGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.categoryDeleteConfirmation);
                gridPosition = position;

                builder.setPositiveButton(R.string.categoryDeleteAccept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CategoryPOJO itemClicked = (CategoryPOJO) cropsGridView.getItemAtPosition(gridPosition);
                                CropsDatabase.getInstance(getContext()).categoryDao().deleteCategory(itemClicked);
                            }
                        }).start();
                    }
                });

                builder.setNegativeButton(R.string.categoryDeleteDenied, null);
                builder.create().show();
                return false;
            }
        });


        return cropView;
    }

    public void getMockTask(){
        ArrayList<CategoryPOJO> categoryList = new ArrayList<>();
        for(int i = 0; i < 20 ; i ++){
            categoryList.add(new CategoryPOJO());
        }
        categoryAdapter.addAll(categoryList);
        categoryAdapter.notifyDataSetChanged();
    }

    public void setCategoryAdapter(List<CategoryPOJO> list){
        categoryAdapter.addAll(list);
        categoryAdapter.notifyDataSetChanged();

    }
}
