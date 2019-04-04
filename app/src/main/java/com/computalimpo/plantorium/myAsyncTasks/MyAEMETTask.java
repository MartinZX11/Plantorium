package com.computalimpo.plantorium.myAsyncTasks;

import android.arch.persistence.room.util.StringUtil;
import android.content.res.AssetManager;
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
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;

import javax.net.ssl.HttpsURLConnection;

public class MyAEMETTask extends AsyncTask<String, Void, JSONArray> {
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
    protected JSONArray doInBackground(String... params) {
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
        String res = obtenerCodigoMunicipio(params[0]);
        builder.appendPath(res);
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
            //Log.d("AVORE", sb.toString());

            result = new JSONArray(sb.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public  String obtenerCodigoMunicipio(String mun) {
        String res = "";
        mun = sanitizarString(mun);
        Integer min_dist_levenshtein = Integer.MAX_VALUE;
        try {
            AssetManager assetManager = activity.get().getActivity().getBaseContext().getAssets();
            InputStreamReader is = new InputStreamReader(assetManager.open("cod_mun.csv"), "ISO-8859-1");
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String sectors[];
            while((line = reader.readLine()) != null) {
                //Ejem: 10;12;104;2;Segorbe -> ['10', '12', '104', '2', 'Segorbe'] donde cod='12104'
                sectors = line.split(";");
                String clean_mun = sanitizarString(sectors[4]);
                //Si el nombre del municipio tiene dos acepciones
                if(clean_mun.contains("/")) {
                    String names[] = clean_mun.split("/");
                    //Si hace match con la primera acepcion
                    if (names[0].matches("(.*)"+mun+"(.*)")) {
                        if (distance(mun, names[0]) < min_dist_levenshtein) {
                            min_dist_levenshtein = distance(mun, names[0]);
                            //Log.d("MUNICIPIO_0", names[0]);
                            res = sectors[1].concat(sectors[2]);
                            //Log.d("MUNICIPIO", res);
                        }
                    }
                    if (names[1].matches("(.*)"+mun+"(.*)")) {
                        if (distance(mun, names[1]) < min_dist_levenshtein) {
                            min_dist_levenshtein = distance(mun, names[1]);
                            //Log.d("MUNICIPIO_1", names[1]);
                            res = sectors[1].concat(sectors[2]);
                            //Log.d("MUNICIPIO", res);
                        }
                    }
                } else {
                    if (clean_mun.matches("(.*)"+mun+"(.*)")) {
                        if (distance(clean_mun, mun) < min_dist_levenshtein) {
                            min_dist_levenshtein = distance(clean_mun, mun);
                            //Log.d("MUNICIPIO", clean_mun);
                            //Log.d("MUNICIPIO", min_dist_levenshtein.toString());
                            res = sectors[1].concat(sectors[2]);
                            //Log.d("MUNICIPIO", res);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public StringBuilder obtenerPrediccionAEMET(String datos) {
        StringBuilder sBuilder = null;
        try {
            URL url = new URL(datos);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "ISO-8859-1"));
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

    public String sanitizarString(String s) {
        String clean_mun = Normalizer.normalize(s, Normalizer.Form.NFD);
        clean_mun = clean_mun.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        clean_mun = clean_mun.toLowerCase();
        return clean_mun;
    }
}
