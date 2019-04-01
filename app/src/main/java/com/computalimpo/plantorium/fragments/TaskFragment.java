package com.computalimpo.plantorium.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.computalimpo.plantorium.AddTaskActivity;
import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.adapters.TaskAdapter;
import com.computalimpo.plantorium.helper.TaskType;

import java.util.ArrayList;

public class TaskFragment extends Fragment {

    TaskAdapter taskAdapter;

    public TaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View cropView = inflater.inflate(R.layout.fragment_task, null);

        taskAdapter = new TaskAdapter(getContext(), R.layout.task_row_item, new ArrayList<TaskPOJO>());

        ListView taskList = cropView.findViewById(R.id.taskList);
        taskList.setAdapter(taskAdapter);
        getMockTask();
        FloatingActionButton addTask = cropView.findViewById(R.id.addTaskFloatingButton);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
        return cropView;
    }

    public void getMockTask(){
        ArrayList<TaskPOJO> taskList = new ArrayList<>();
        for(int i = 0; i < 20 ; i ++){
            taskList.add(new TaskPOJO(i,i / 5 + "/10/2019" , "WATER","AGUA","" ));
        }
        String aux;
        aux = taskList.get(0).getDate();
        taskAdapter.addSectionHeaderItem(taskList.get(0));
        for(TaskPOJO task : taskList){
            if(task.getDate().equals(aux)){
                taskAdapter.addItem(task);
            }else{
                taskAdapter.addSectionHeaderItem(task);
                taskAdapter.addItem(task);
                aux = task.getDate();
            }
        }
        taskAdapter.notifyDataSetChanged();
        return;
    }
}
