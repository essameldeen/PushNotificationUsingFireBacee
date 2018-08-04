package com.example.toshiba.pushnotificationusingfirebace.service;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.toshiba.pushnotificationusingfirebace.R;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String messageTitle = remoteMessage.getNotification().getTitle();
        String messageBody = remoteMessage.getNotification().getBody();
        String message = remoteMessage.getData().get("message");
        String from = remoteMessage.getData().get("from_user_id");


        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(messageTitle)
                .setContentText(messageBody);

        Intent intent = new Intent(remoteMessage.getNotification().getClickAction());
        intent.putExtra("message",message);
        intent.putExtra("from",from);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
             int notificationId= (int) System.currentTimeMillis();
             NotificationManager manager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
             manager.notify(notificationId,builder.build());
    }
}
