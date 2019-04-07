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

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.crop_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.category_list_name);
            holder.id = (TextView) convertView.findViewById(R.id.category_list_id);

            convertView.setTag(holder);
        }

        CategoryPOJO currentCategory = (CategoryPOJO) getItem(position);
        holder = (ViewHolder) convertView.getTag();

        holder.name.setText(currentCategory.getName());
        String s = String.valueOf( currentCategory.getId());
        holder.id.setText(s);

        return convertView;
    }

    private class ViewHolder {

             TextView id;
             TextView  name;

             public ViewHolder(){
                 this.name = null;
                 this.id = null;
             }
        }
    }