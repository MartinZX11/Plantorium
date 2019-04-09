package com.computalimpo.plantorium.myAsyncTasks;

import android.os.AsyncTask;

import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.computalimpo.plantorium.fragments.TaskFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TaskAsyncTask extends AsyncTask<Boolean,Void, List<TaskPOJO>> {
    private WeakReference<TaskFragment> taskFragmentWeakReference;
    private CropsDatabase cropsDatabase;

    public TaskAsyncTask(TaskFragment taskFragmentWeakReference) {
        this.taskFragmentWeakReference = new WeakReference<>(taskFragmentWeakReference);
        cropsDatabase = CropsDatabase.getInstance(taskFragmentWeakReference.getContext());

    }

    @Override
    protected List<TaskPOJO> doInBackground(Boolean... booleans) {
        List<TaskPOJO> list;

        list = CropsDatabase.getInstance(taskFragmentWeakReference.get().getContext()).taskDao().getTask();
        return list;
    }

    @Override
    protected void onPostExecute(List<TaskPOJO> task) {
        super.onPostExecute(task);
        taskFragmentWeakReference.get().setTaskAdapter(task);
    }

}
