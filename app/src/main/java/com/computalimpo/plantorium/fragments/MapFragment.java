package com.computalimpo.plantorium.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.computalimpo.plantorium.myAsyncTasks.CategoryAsyncTask;
import com.computalimpo.plantorium.myAsyncTasks.MapAsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements com.google.android.gms.maps.OnMapReadyCallback{


    List<CategoryPOJO> list;
    public SupportMapFragment mapFragment;
    public GoogleMap googleMap;
    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mapView = inflater.inflate(R.layout.fragment_map, null);

        return mapView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        MapAsyncTask mapAsyncTask = new MapAsyncTask(this);
        mapAsyncTask.execute(true);
        /*
        final List<CategoryPOJO> categories = CropsDatabase.getInstance(getContext()).categoryDao().getCategories();
        for (CategoryPOJO category : categories) {
            MarkerOptions options = new MarkerOptions();
            options.position(new LatLng(category.getLatitude(), category.getLongitude()));
            options.title(category.getName());
            options.snippet("x" + category.getNumber());
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
            googleMap.addMarker(options);

        }*/

    };



}
