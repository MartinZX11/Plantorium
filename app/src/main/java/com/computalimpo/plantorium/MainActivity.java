package com.computalimpo.plantorium;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.computalimpo.plantorium.fragments.CropsFragment;
import com.computalimpo.plantorium.fragments.MapFragment;
import com.computalimpo.plantorium.fragments.QRFragment;
import com.computalimpo.plantorium.fragments.TaskFragment;
import com.computalimpo.plantorium.fragments.WeatherFragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);
        ((BottomNavigationView) findViewById(R.id.bottomNavigationView)).setOnNavigationItemSelectedListener(this);
        String tag = "crops";
        Fragment fragment=  new CropsFragment();
        getSupportActionBar().setTitle(R.string.crops);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, tag).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        String tag = null;
        switch (menuItem.getItemId()){
            case R.id.crops:
                tag = "crops";
                Log.d("tag1", "onNavigationItemSelected: ");
                fragment= getSupportFragmentManager().findFragmentByTag(tag);
                if(fragment== null) {
                    fragment=  new CropsFragment();
                }getSupportActionBar().setTitle(R.string.crops);
                break;
            case R.id.map:
                tag = "map";
                Log.d("tag1", "onNavigationItemSelected: ");
                fragment= getSupportFragmentManager().findFragmentByTag(tag);
                if(fragment== null) {
                    fragment = new MapFragment();
                }getSupportActionBar().setTitle(R.string.map);
                break;
            case R.id.qr:
                tag = "qr";
                Log.d("tag1", "onNavigationItemSelected: ");
                fragment= getSupportFragmentManager().findFragmentByTag(tag);
                if(fragment== null) {
                    fragment=  new QRFragment();
                }getSupportActionBar().setTitle(R.string.qr);
                break;
            case R.id.weather:
                tag = "weather";
                Log.d("tag1", "onNavigationItemSelected: ");
                fragment= getSupportFragmentManager().findFragmentByTag(tag);
                if(fragment== null) {
                    fragment=  new WeatherFragment();
                }getSupportActionBar().setTitle(R.string.weather);
                break;
            case R.id.task:
                tag = "task";
                Log.d("tag1", "onNavigationItemSelected: ");
                fragment= getSupportFragmentManager().findFragmentByTag(tag);
                if(fragment== null) {
                    fragment=  new TaskFragment();
                }getSupportActionBar().setTitle(R.string.task);
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, tag).commit();
        return true;


    }




}
