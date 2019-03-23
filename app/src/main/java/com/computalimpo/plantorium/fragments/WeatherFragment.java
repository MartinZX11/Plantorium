package com.computalimpo.plantorium.fragments;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.myAsyncTasks.MyAEMETTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class WeatherFragment extends Fragment {
    private View weather_view = null;
    MyAEMETTask weather_task;
    JSONArray infoAEMET = null;
    Button tomorrowButton;
    Button todayButton;
    TextView porcentaje_rain, text_max_temp, text_min_temp, min_moisture, max_moisture, slot1_dir, slot1_speed, slot2_dir, slot2_speed, slot3_dir, slot3_speed, slot4_dir, slot4_speed;
    ImageView general_info, min_temp_icon, max_temp_icon, moisture_icon, wind_icon;
    TextView title_prob_rain, title_min_moisture, title_max_moisture, header_time, header_dir, header_speed, slot1_time, slot2_time, slot3_time, slot4_time;

    public WeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        weather_view = inflater.inflate(R.layout.fragment_weather, null);

        if (hayConexion()) {
            weather_task = new MyAEMETTask(this);
            weather_task.execute();
        } else {
            Toast.makeText(getActivity(), "No dispones de conexión a Internet", Toast.LENGTH_SHORT).show();
        }

        tomorrowButton = weather_view.findViewById(R.id.tomorrowButton);
        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomorrowButton.setVisibility(View.INVISIBLE);
                todayButton.setVisibility(View.VISIBLE);

                obtenerWeatherInfo(infoAEMET, 1);
                //refreshUI(info_weather);
            }
        });

        todayButton = weather_view.findViewById(R.id.todayButton);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayButton.setVisibility(View.INVISIBLE);
                tomorrowButton.setVisibility(View.VISIBLE);

                obtenerWeatherInfo(infoAEMET, 0);
                //refreshUI(info_weather);
            }
        });

        //Obtener referencias para el refresco de la UI
        porcentaje_rain = weather_view.findViewById(R.id.porcentaje_rain);
        text_max_temp = weather_view.findViewById(R.id.text_max_temp);
        text_min_temp = weather_view.findViewById(R.id.text_min_temp);
        max_moisture = weather_view.findViewById(R.id.max_moisture);
        min_moisture = weather_view.findViewById(R.id.min_moisture);
        slot1_dir = weather_view.findViewById(R.id.slot1_dir);
        slot1_speed = weather_view.findViewById(R.id.slot1_speed);
        slot2_dir = weather_view.findViewById(R.id.slot2_dir);
        slot2_speed = weather_view.findViewById(R.id.slot2_speed);
        slot3_dir = weather_view.findViewById(R.id.slot3_dir);
        slot3_speed = weather_view.findViewById(R.id.slot3_speed);
        slot4_dir = weather_view.findViewById(R.id.slot4_dir);
        slot4_speed = weather_view.findViewById(R.id.slot4_speed);
        general_info = weather_view.findViewById(R.id.general_info);
        title_prob_rain = weather_view.findViewById(R.id.prob_rain);
        min_temp_icon = weather_view.findViewById(R.id.min_temp_icon);
        max_temp_icon = weather_view.findViewById(R.id.max_temp_icon);
        moisture_icon = weather_view.findViewById(R.id.moisture_icon);
        title_min_moisture = weather_view.findViewById(R.id.title_min_moisture);
        title_max_moisture = weather_view.findViewById(R.id.title_max_moisture);
        wind_icon = weather_view.findViewById(R.id.wind_icon);
        header_time =weather_view.findViewById(R.id.header_time);
        header_dir =weather_view.findViewById(R.id.header_dir);
        header_speed =weather_view.findViewById(R.id.header_speed);
        slot1_time = weather_view.findViewById(R.id.slot1_time);
        slot2_time = weather_view.findViewById(R.id.slot2_time);
        slot3_time = weather_view.findViewById(R.id.slot3_time);
        slot4_time = weather_view.findViewById(R.id.slot4_time);

        return weather_view;
    }

    public void loadWeather() {
        ProgressBar pb = weather_view.findViewById(R.id.load_weather);
        pb.setVisibility(View.VISIBLE);
    }

    public void finishLoadWeather(JSONArray json) {
        //Ya se han cargado los datos, entonces se hace invisible el progressBar
        ProgressBar pb = weather_view.findViewById(R.id.load_weather);
        pb.setVisibility(View.INVISIBLE);

        porcentaje_rain.setVisibility(View.VISIBLE);
        text_max_temp.setVisibility(View.VISIBLE);
        text_min_temp.setVisibility(View.VISIBLE);
        max_moisture.setVisibility(View.VISIBLE);
        min_moisture.setVisibility(View.VISIBLE);
        slot1_dir.setVisibility(View.VISIBLE);
        slot1_speed.setVisibility(View.VISIBLE);
        slot2_dir.setVisibility(View.VISIBLE);
        slot2_speed.setVisibility(View.VISIBLE);
        slot3_dir.setVisibility(View.VISIBLE);
        slot3_speed.setVisibility(View.VISIBLE);
        slot4_dir.setVisibility(View.VISIBLE);
        slot4_speed.setVisibility(View.VISIBLE);
        general_info.setVisibility(View.VISIBLE);
        title_prob_rain.setVisibility(View.VISIBLE);
        min_temp_icon.setVisibility(View.VISIBLE);
        max_temp_icon.setVisibility(View.VISIBLE);
        moisture_icon.setVisibility(View.VISIBLE);
        title_min_moisture.setVisibility(View.VISIBLE);
        title_max_moisture.setVisibility(View.VISIBLE);
        wind_icon.setVisibility(View.VISIBLE);
        header_time.setVisibility(View.VISIBLE);
        header_dir.setVisibility(View.VISIBLE);
        header_speed.setVisibility(View.VISIBLE);
        slot1_time.setVisibility(View.VISIBLE);
        slot2_time.setVisibility(View.VISIBLE);
        slot3_time.setVisibility(View.VISIBLE);
        slot4_time.setVisibility(View.VISIBLE);

        //Ya puede interaccionar el usario, el cual se encuenra en el today_info
        tomorrowButton.setVisibility(View.VISIBLE);
        ////Referencia al JSON de AEMET////
        infoAEMET = json;

        //Obtener datos de hoy(valor 0)
        obtenerWeatherInfo(json, 0);

        //Actualizar interfaz gráfica
        //refreshUI(info_weather);

    }

    public void obtenerWeatherInfo(JSONArray json, int select_dia) {
        String dir, velocidad, hora;
        try {

            JSONObject dia = json.getJSONObject(0).getJSONObject("prediccion").getJSONArray("dia").getJSONObject(select_dia);
            int porcentaje_lluvia = Integer.parseInt(dia.getJSONArray("probPrecipitacion").getJSONObject(0).getString("value"));

            concludeSky(porcentaje_lluvia);

            porcentaje_rain.setText(dia.getJSONArray("probPrecipitacion").getJSONObject(0).getString("value"));

            text_max_temp.setText(dia.getJSONObject("temperatura").getString("maxima"));
            text_min_temp.setText(dia.getJSONObject("temperatura").getString("minima"));
            max_moisture.setText(dia.getJSONObject("humedadRelativa").getString("maxima"));
            min_moisture.setText(dia.getJSONObject("humedadRelativa").getString("minima"));

            //Rachas de viento por franja horaria
            JSONArray vientos = dia.getJSONArray("viento");
            JSONObject viento;

            for(int i = 3; i < vientos.length(); i++) {
                viento = vientos.getJSONObject(i);
                switch(i) {
                    case 3:
                        slot1_dir.setText(viento.getString("direccion"));
                        slot1_speed.setText(viento.getString("velocidad"));
                    case 4:
                        slot2_dir.setText(viento.getString("direccion"));
                        slot2_speed.setText(viento.getString("velocidad"));
                    case 5:
                        slot3_dir.setText(viento.getString("direccion"));
                        slot3_speed.setText(viento.getString("velocidad"));
                    case 6:
                        slot4_dir.setText(viento.getString("direccion"));
                        slot4_speed.setText(viento.getString("velocidad"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void concludeSky(int p) {
        if(p >= 0 && p <= 20) {
            general_info.setImageResource(R.drawable.sun_icon);
        }else if(p > 20 && p <= 60) {
            general_info.setImageResource(R.drawable.cloud_icon);
        }else {
            general_info.setImageResource(R.drawable.rain_icon);
        }
    }

    public boolean hayConexion() {
        boolean hayConexion = false;
        if (getActivity() != null) {
            ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
            hayConexion = (manager.getActiveNetworkInfo() != null) && (manager.getActiveNetworkInfo().isConnected());
        }
        return hayConexion;
    }

    @Override
    public void onDestroyView() {
        if ((weather_task != null) && (weather_task.getStatus() == AsyncTask.Status.RUNNING)) {
            weather_task.cancel(true);
        }
        super.onDestroyView();
    }
}
