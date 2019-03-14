package com.computalimpo.plantorium.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.adapters.TaskAdapter;

import java.util.ArrayList;

public class TaskFragment extends Fragment {

    TaskAdapter taskAdapter;

    public TaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View cropView = inflater.inflate(R.layout.fragment_task, null);

        taskAdapter = new TaskAdapter(getContext(), R.layout.task_row, new ArrayList<TaskPOJO>());

        ListView taskList = cropView.findViewById(R.id.taskList);
        taskList.setAdapter(taskAdapter);
        taskAdapter.addAll(getMockTask());
        FloatingActionButton addTask = cropView.findViewById(R.id.addTaskFloatingButton);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "add_task";
                Fragment fragment = getFragmentManager().findFragmentByTag(tag);
                if(fragment== null) {
                    fragment = new AddTaskFragment();
                }((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.add_task);
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, tag).commit();

            }
        });
        return cropView;
    }

    public ArrayList<TaskPOJO> getMockTask(){
        ArrayList<TaskPOJO> taskList = new ArrayList<>();

        for(int i = 0; i < 20 ; i ++){
            TaskPOJO aux;
            taskList.add(new TaskPOJO("CROP 0", "MEDIUM"));
            if(i % 3 == 0){
                aux = new TaskPOJO("CROP" + i, "HIGH");
            }else {
                aux = new TaskPOJO("CROP" + i, "LOW");
            }
            taskList.add(aux);
        }

        return taskList;
    }
}
