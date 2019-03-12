package com.computalimpo.plantorium.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.computalimpo.plantorium.R;

public class CropsFragment extends Fragment {

    public CropsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View cropView = inflater.inflate(R.layout.fragment_crops, null);
        FloatingActionButton addCrop = cropView.findViewById(R.id.addCrop);
        addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cropView.getContext(), R.string.crops, Toast.LENGTH_SHORT).show();
            }
        });
        return cropView;
    }
}
