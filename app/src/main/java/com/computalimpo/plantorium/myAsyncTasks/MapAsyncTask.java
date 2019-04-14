package com.computalimpo.plantorium.myAsyncTasks;

import android.os.AsyncTask;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.computalimpo.plantorium.fragments.CropsFragment;
import com.computalimpo.plantorium.fragments.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.WeakReference;
import java.util.List;

public class MapAsyncTask extends AsyncTask<Boolean,Void, List<CategoryPOJO>> {
    private WeakReference<MapFragment> mapFragmentWeakReference;
    private CropsDatabase cropsDatabase;
    private GoogleMap map;

    public MapAsyncTask(MapFragment mapFragmentWeakReference) {
        this.mapFragmentWeakReference = new WeakReference<>(mapFragmentWeakReference);
        cropsDatabase = CropsDatabase.getInstance(mapFragmentWeakReference.getContext());

    }

    @Override
    protected List<CategoryPOJO> doInBackground(Boolean... booleans) {
        List<CategoryPOJO> list;

        list = CropsDatabase.getInstance(mapFragmentWeakReference.get().getContext()).categoryDao().getCategories();
        return list;
    }

    @Override
    protected void onPostExecute(List<CategoryPOJO> category) {
        super.onPostExecute(category);
        for (CategoryPOJO categoria : category) {
            MarkerOptions options = new MarkerOptions();
            options.position(new LatLng(categoria.getLatitude(), categoria.getLongitude()));
            options.title(categoria.getName());
            options.snippet("x" + categoria.getNumber());
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
            mapFragmentWeakReference.get().googleMap.addMarker(options);

        }

    }
}
