package com.computalimpo.plantorium.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryPOJO> {

        private Context context;
        private LayoutInflater mInflater;
        private List<CategoryPOJO> categoryList;

    public CategoryAdapter(Context context, int resource, List<CategoryPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.categoryList = objects;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final CategoryPOJO item) {
        categoryList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public CategoryPOJO getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskAdapter.ViewHolder holder = null;

        if (convertView == null) {
            holder = new TaskAdapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.task_row_header, null);
            holder.textView = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (TaskAdapter.ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public static class ViewHolder {
            public TextView textView;
        }
    }