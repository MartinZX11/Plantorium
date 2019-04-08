package com.computalimpo.plantorium.Receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.fragments.WeatherFragment;

public class NotificationWeatherReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent weather_intent = new Intent(context, WeatherFragment.class);
        weather_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, weather_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.our_app_icon)
                .setContentTitle("Plantorium_Weather")
                .setContentText("He logrado crear una notificacion diaria")
                .setAutoCancel(true);
        notificationManager.notify(100, builder.build());
    }
}
