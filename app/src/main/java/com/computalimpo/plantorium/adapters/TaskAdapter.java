package com.computalimpo.plantorium.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.R;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskPOJO> {

    private Context context;
    private List<TaskPOJO> taskList;

    public TaskAdapter(Context context, int resource, List<TaskPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.taskList = objects;
    }
    //TODO: Mirar de meter un ViewHolder por el coste del findviewbyid

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskPOJO task = taskList.get(position);
        View row;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.task_row, parent, false);
        } else {
            row = convertView;
        }

        TextView taskText = row.findViewById(R.id.taskText);
        taskText.setText(task.getTaskText());
        switch (task.getPriority()){
            case "LOW":
                taskText.setBackgroundColor(Color.GREEN);
                break;
            case "MEDIUM":
                taskText.setBackgroundColor(Color.YELLOW);
                break;
            case "HIGH":
                taskText.setBackgroundColor(Color.RED);
                break;
        }
        return row;
    }
}
