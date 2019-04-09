package com.computalimpo.plantorium.Receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.computalimpo.plantorium.MainActivity;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.fragments.WeatherFragment;
import com.computalimpo.plantorium.myAsyncTasks.MyAEMETTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NotificationWeatherReceiver extends BroadcastReceiver {

    MyAEMETTask weather_task;
    Context myContext;
    int i = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        myContext = context;

        //PETICION DE DATOS A LA API DE AEMET
        //Se deberán recuperar los distintos municipios de la bbdd
        String municipios[] = {"Segorbe", "Sagunto", "Cheste"};
        Integer cont = 0;
        for (int i = 0; i < municipios.length; i++) {
            launchAEMETRequest(municipios[i]);
            Log.d("NOTIFICATE", "se piden datos de " + municipios[i]);
        }
    }

    public void launchAEMETRequest(String municipio) {
        if (hayConexion(myContext)) {
            weather_task = new MyAEMETTask(null, this, myContext);
            weather_task.execute(municipio);
        }
    }

    public boolean hayConexion(Context context) {
        boolean hayConexion = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        hayConexion = (manager.getActiveNetworkInfo() != null) && (manager.getActiveNetworkInfo().isConnected());
        return hayConexion;
    }
}
