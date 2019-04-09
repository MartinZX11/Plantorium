package com.computalimpo.plantorium.myAsyncTasks;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.util.StringUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.Receivers.NotificationWeatherReceiver;
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
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class MyAEMETTask extends AsyncTask<String, Void, JSONArray> {
    private WeakReference<WeatherFragment> activity = null;
    private WeakReference<NotificationWeatherReceiver> receiver = null;
    BroadcastReceiver.PendingResult pendingResult;
    private Context myContext;
    NotificationManager notificationManager;
    PendingIntent pendingIntent;
    Boolean acabado = false;

    public MyAEMETTask(WeatherFragment weatherAct, NotificationWeatherReceiver receiverAct, Context context){
        this.activity = new WeakReference<>(weatherAct);
        this.receiver = new WeakReference<>(receiverAct);
        myContext = context;
        this.pendingResult = pendingResult;
    }

    @Override
    protected void onPreExecute() {
        if (activity.get() != null) {
            this.activity.get().loadWeather();
        }
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONArray s) {
        if (activity.get() != null) {
            this.activity.get().finishLoadWeather(s);
            Log.d("NOTIFICATE", "maaal");
        }
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

        if (activity.get() == null) {
            //////////////////////////////
            String title, municipio;
            int porcentaje_lluvia, min_temp, max_temp;

            int select_dia = 1; //Tiempo de mañana

            try {
                municipio = result.getJSONObject(0).getString("nombre")  + ", " + result.getJSONObject(0).getString("provincia");

                JSONObject dia = result.getJSONObject(0).getJSONObject("prediccion").getJSONArray("dia").getJSONObject(select_dia);

                porcentaje_lluvia = Integer.parseInt(dia.getJSONArray("probPrecipitacion").getJSONObject(0).getString("value"));
                max_temp = Integer.parseInt(dia.getJSONObject("temperatura").getString("maxima"));
                min_temp = Integer.parseInt(dia.getJSONObject("temperatura").getString("minima"));

                //Rachas de viento por franja horaria
                JSONArray vientos = dia.getJSONArray("viento");
                JSONObject viento;

                for(int i = 3; i < vientos.length(); i++) {
                    viento = vientos.getJSONObject(i);
                    if (Integer.parseInt(viento.getString("velocidad")) > 50) {
                        makeNotification(myContext.getResources().getString(R.string.riesgoRacha), municipio);
                        Log.d("NOTIFICATE", "riesgo rachas");
                    }
                }

                //Flags para saber que notificaciones crear
                if (porcentaje_lluvia > 80) {makeNotification(myContext.getResources().getString(R.string.riesgoAltaProb), municipio); Log.d("NOTIFICATE", "alta prob llover"); }
                if (min_temp < 3) { makeNotification(myContext.getResources().getString(R.string.riesgoHelada), municipio); Log.d("NOTIFICATE", "riesgo helada"); }
                if (max_temp > 24 && porcentaje_lluvia < 40) { makeNotification(myContext.getResources().getString(R.string.riesgoCalido), municipio); Log.d("NOTIFICATE", "riesgo calido"); }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    public void makeNotification(String content, String title) {
        notificationManager = (NotificationManager) myContext.getSystemService(myContext.NOTIFICATION_SERVICE);
        Intent weather_intent = new Intent(myContext, WeatherFragment.class);
        weather_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(myContext, 100, weather_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.weather_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);

        //De esta manera se mostrarán de forma individual y no se sobreescriben
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        notificationManager.notify(m, builder.build());
    }

    public  String obtenerCodigoMunicipio(String mun) {
        String res = "";
        mun = sanitizarString(mun);
        Integer min_dist_levenshtein = Integer.MAX_VALUE;
        AssetManager assetManager;
        try {
            if (activity.get() != null) {
                assetManager = activity.get().getActivity().getBaseContext().getAssets();
            } else {
                assetManager = myContext.getAssets();
            }
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
