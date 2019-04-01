package com.computalimpo.plantorium.myAsyncTasks;

import android.os.AsyncTask;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.fragments.CropsFragment;
import com.computalimpo.plantorium.database.CropsDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class CategoryAsyncTask extends AsyncTask<Boolean,Void, List<CategoryPOJO>> {
    private WeakReference<CropsFragment> cropFragmentWeakReference;
    private CropsDatabase cropsDatabase;

    public CategoryAsyncTask(CropsFragment cropFragmentWeakReference) {
        this.cropFragmentWeakReference = new WeakReference<>(cropFragmentWeakReference);
        cropsDatabase = CropsDatabase.getInstance(cropFragmentWeakReference.getContext());

    }

    @Override
    protected List<CategoryPOJO> doInBackground(Boolean... booleans) {
        List<CategoryPOJO> list;

        list = CropsDatabase.getInstance(cropFragmentWeakReference.get().getContext()).categoryDao().getCategories();



        return list;
    }

    @Override
    protected void onPostExecute(List<CategoryPOJO> category) {
        super.onPostExecute(category);
        cropFragmentWeakReference.get().setCategoryAdapter(category);
    }

}
