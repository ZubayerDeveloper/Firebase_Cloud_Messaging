package zubayer.note;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageReceive extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setAutoCancel(true)
                .setLights(Color.BLUE,1,1);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify("we", 1020, notificationBuilder.build());
    }
    //    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        if (Build.VERSION.SDK_INT >= 26) {
//            NotificationChannel channel = new NotificationChannel("firebase", "firebaseNotification", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(channel);
//            }
//            Notification notification = new NotificationCompat.Builder(this)
//                    .setContentTitle("oooooooooooo")
//                    .setContentText(remoteMessage.getNotification().getBody())
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setChannelId("firebase").build();
//            notificationManager.notify(012, notification);
//        }else {
//            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
//            bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
//            bigTextStyle.bigText(remoteMessage.getData().get("message"));
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                    .setContentTitle(remoteMessage.getData().get("title"))
//                    .setContentText(remoteMessage.getData().get("message"))
//                    .setColor(0xff990000)
//                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                    .setWhen(System.currentTimeMillis())
//                    .setPriority(Notification.PRIORITY_MAX)
//                    .setLights(0xff990000, 300, 100)
//                    .setStyle(bigTextStyle)
//                    .setSmallIcon(R.mipmap.ic_launcher);
//
//            notificationManager.notify(013, notificationBuilder.build());
//        }
//        if (remoteMessage.getNotification() != null) {
//            Log.d("notti", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//    }
}
