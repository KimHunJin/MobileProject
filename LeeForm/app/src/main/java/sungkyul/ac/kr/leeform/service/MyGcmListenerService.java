package sungkyul.ac.kr.leeform.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

import java.net.URLDecoder;

import sungkyul.ac.kr.leeform.MainActivity;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.gcm.PopUpActivity;

/**
 * Created by HunJin on 2016-06-10.
 */
public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        try {
            String message = data.getString("ellord");
            message = URLDecoder.decode(message, "utf-8");
            Log.e(TAG, "From: " + from);
            Log.e(TAG, "Message: " + message);

            if (from.startsWith("/topics/")) {
                // message received from some topic.
            } else {
                // normal downstream message.
            }

            // [START_EXCLUDE]
            /**
             * Production applications would usually process the message here.
             * Eg: - Syncing with server.
             *     - Store message in local database.
             *     - Update UI.
             */

            /**
             * In some cases it may be useful to show a notification indicating to the user
             * that a message was received.
             */
            sendNotification(message);
            getMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void getMessage(String message) {

        // 만약 팝업 알림 설정이 켜져있으면 실행한다.
        // 팝업으로 사용할 액티비티를 호출할 인텐트를 작성한다.
        Intent popupIntent = new Intent(getApplicationContext(),PopUpActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        popupIntent.putExtra("message", message);
        // 그리고 호출한다.
        getApplication().startActivity(popupIntent);
    }
}
