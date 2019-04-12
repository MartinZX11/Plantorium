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
import com.computalimpo.plantorium.myAsyncTasks.TaskAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    TaskAdapter taskAdapter;

    public TaskFragment() {}


    @Override
    public void onResume() {
        super.onResume();

        TaskAsyncTask taskAsyncTask = new TaskAsyncTask(this);
        taskAsyncTask.execute(true);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View cropView = inflater.inflate(R.layout.fragment_task, null);

        TaskAsyncTask taskAsyncTask = new TaskAsyncTask(this);

        taskAdapter = new TaskAdapter(getContext(), R.layout.task_row_item, new ArrayList<TaskPOJO>());

        taskAsyncTask.execute(true);
        ListView taskList = cropView.findViewById(R.id.taskList);
        taskList.setAdapter(taskAdapter);
        //getMockTask();
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

    public void setTaskAdapter(List<TaskPOJO> taskList){
        taskAdapter.clear();
        if(!taskList.isEmpty()) {
            String aux;



            aux = taskList.get(0).getDate();
            taskAdapter.addSectionHeaderItem(taskList.get(0));
            for (TaskPOJO task : taskList) {
                if (task.getDate().equals(aux)) {
                    taskAdapter.addItem(task);
                } else {
                    taskAdapter.addSectionHeaderItem(task);
                    taskAdapter.addItem(task);
                    aux = task.getDate();
                }
            }
            taskAdapter.notifyDataSetChanged();
        }
        return;
    }




}
