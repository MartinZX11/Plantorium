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
import android.widget.ListView;

import com.computalimpo.plantorium.AddTaskActivity;
import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.adapters.TaskAdapter;
import com.computalimpo.plantorium.database.CropsDatabase;
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
        final ListView taskList = cropView.findViewById(R.id.taskList);
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

        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(taskAdapter.getItemViewType(position) == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.categoryDeleteConfirmation);
                    final TaskPOJO cp = taskAdapter.getItem(position);

                    builder.setPositiveButton(R.string.categoryDeleteAccept, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            taskAdapter.removeItem(cp);
                            taskAdapter.notifyDataSetChanged();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    CropsDatabase.getInstance(getContext()).taskDao().deleteTask(cp);
                                }
                            }).start();
                        }
                    });

                    builder.setNegativeButton(R.string.categoryDeleteDenied, null);
                    builder.create().show();
                    return true;
                }
                return true;
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
