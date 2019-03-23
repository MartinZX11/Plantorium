package com.computalimpo.plantorium.myAsyncTasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.computalimpo.plantorium.fragments.WeatherFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyAEMETTask extends AsyncTask<Void, Void, JSONArray> {
    private WeakReference<WeatherFragment> activity;

    public MyAEMETTask(WeatherFragment weatherAct){
        this.activity = new WeakReference<>(weatherAct);
    }

    @Override
    protected void onPreExecute() {
        this.activity.get().loadWeather();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONArray s) {
        this.activity.get().finishLoadWeather(s);
        super.onPostExecute(s);
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        JSONArray result = null;

        //////////////////////////////////////////////////////////////////////
        //EN PRIMER LUGAR OBTENEMOS LA RUTA DE LOS DATOS REQUERIDOS DE AEMET//
        /////////////////////////////////////////////////////////////////////

        Uri.Builder builder = new Uri.Builder();
        //https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/12104
        builder.scheme("https");
        builder.authority("opendata.aemet.es");
        builder.appendPath("opendata");
        builder.appendPath("api");
        builder.appendPath("prediccion");
        builder.appendPath("especifica");
        builder.appendPath("municipio");
        builder.appendPath("diaria");
        //Municipio a piñon fijo POR EL MOMENTO
        builder.appendPath("12104");
        builder.appendPath("");
        builder.appendQueryParameter("api_key", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZGdpbWVubzg3QGhvdG1haWwuY29tIiwianRpIjoiN2ViMGViYzctOWQ4Zi00Yzg3LTg1NzYtNmQ1YTYxMTBlYmI4IiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NTMwODM1ODgsInVzZXJJZCI6IjdlYjBlYmM3LTlkOGYtNGM4Ny04NTc2LTZkNWE2MTEwZWJiOCIsInJvbGUiOiIifQ.twSOeMoMsJqAo1yd_6EzZ-2BfznPOMOQ3MiXKyGBmLI");

        try {
            StringBuilder sb = obtenerPrediccionAEMET(builder.build().toString());
            JSONObject jsonObject = new JSONObject(sb.toString());
            String datos = jsonObject.getString("datos");

            //////////////////////////////////////////////////
            //AHORA OBTENEMOS EL JSON DE LA PREDICCION AEMET//
            //////////////////////////////////////////////////
            sb = obtenerPrediccionAEMET(datos);
            Log.d("AVORE", sb.toString());

            result = new JSONArray(sb.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public StringBuilder obtenerPrediccionAEMET(String datos) {
        StringBuilder sBuilder = null;
        try {
            URL url = new URL(datos);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sBuilder = new StringBuilder();
                String s;
                while ((s = reader.readLine()) != null) {
                    sBuilder.append(s);
                }
                reader.close();
            }
            connection.disconnect();
        } catch (IOException e) { e.printStackTrace(); }

        return sBuilder;
    }
}
