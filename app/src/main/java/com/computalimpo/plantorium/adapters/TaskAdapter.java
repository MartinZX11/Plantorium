package com.computalimpo.plantorium.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.R;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.TreeSet;

public class TaskAdapter extends ArrayAdapter<TaskPOJO> {

    private Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private LayoutInflater mInflater;
    private List<TaskPOJO> taskList;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    public TaskAdapter(Context context, int resource, List<TaskPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.taskList = objects;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final TaskPOJO item) {
        taskList.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final TaskPOJO item) {
        taskList.add(item);
        sectionHeader.add(taskList.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public TaskPOJO getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(final TaskPOJO item) {
        taskList.remove(item);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.task_row_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.task_row_header, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (rowType) {
            case TYPE_ITEM:
                holder.textView.setText(taskList.get(position).getTextForFragment());
                break;
            case TYPE_SEPARATOR:
                holder.textView.setText(taskList.get(position).getDate());
                break;
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
