package com.computalimpo.plantorium.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.adapters.CategoryAdapter;

import java.util.ArrayList;

public class CropsFragment extends Fragment {

    CategoryAdapter categoryAdapter;

    public CropsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View cropView = inflater.inflate(R.layout.fragment_crops, null);

        FloatingActionButton addCrop = cropView.findViewById(R.id.addCrop);

        categoryAdapter = new CategoryAdapter(getContext(), R.layout.task_row_header, new ArrayList<CategoryPOJO>());
        GridView cropsGridView = cropView.findViewById(R.id.categoryGridView);
        cropsGridView.setAdapter(categoryAdapter);
        getMockTask();
        addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cropView.getContext(), R.string.crops, Toast.LENGTH_SHORT).show();
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
}
