package com.diss.cabadvertisementdriver.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.FireNotiBean;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
 String TAG=MyFirebaseMessagingService.class.getSimpleName();
 Context context;
    AppData appdata;
 @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        context=this;
//        Log.d(TAG, "From11: " + remoteMessage.getFrom());
        Log.d(TAG, "From111: " + remoteMessage.getData());
         String data=remoteMessage.getData().toString();
         String sTitle="",sMsg="",type="",s_id="";
         Log.e("",""+data);
     appdata=new AppData(this);
     appdata.setNotiClick("1");

//     if (appdata.getUserID().equals("NA")||appdata.getUserID()=="NA"||TextUtils.isEmpty(appdata.getUserID())) {//for not getting notification
//
//     }
//     else
         {

         if (!TextUtils.isEmpty(data) && data.contains("{") && data.contains("}")) {
             try {
                 data = data.substring(1, data.length() - 1);
                 String[] data1 = data.split("=");
                 String urlPart = data1[0];
                 String dataPart = data1[1];
                 Log.e("", "dataPart= " + dataPart);

                 Gson g = new Gson();
                 FireNotiBean fireBean = g.fromJson(dataPart, FireNotiBean.class);
                 sTitle = fireBean.getTitle();
                 sMsg = fireBean.getMessage();
                 type = fireBean.getType();
                 s_id = fireBean.getS_id();

             } catch (Exception e) {
                 e.printStackTrace();
             }
             appdata.setNotiType(type);

             if (appdata.getUserID().equals("NA")||appdata.getUserID()=="NA"||TextUtils.isEmpty(appdata.getUserID())) {
                 createNotification(sTitle, sMsg, "login");
             }
             else {
                 appdata.sets_Id(s_id);
                 appdata.setDfffS_Id("0");//
                 Log.e("", "sTitle= " + sTitle + " sMsg= " + sMsg + " type= " + type + " s_id=" + s_id);
                 createNotification(sTitle, sMsg, type);
             }
         } else {
             createNotification(getString(R.string.app_name), remoteMessage.getNotification().getBody(),"");
         }
     }
         
    }
    
    private void createNotification(String title,String messageBody,String messageType) {

            Intent intent = null;
            switch (messageType)
            {
                case "accepted_driver_sticker"://accepted_driver_sticker
                case "campaign_started"://accepted_driver_sticker
                case "rejected_driver_sticker":
                    appdata.setNotiClick("0");
                    appdata.setNotiType("0");
                    intent = new Intent( this , MyCompaignDetailActivity. class );
                    break;

                case "login"://user not logined in
                    intent = new Intent( this , SplashActivity. class );
                    break;
                case "campaign_request":
                default://campaign_request  for accept/reject campaign request
                    appdata.setNotiClick("0");
                    appdata.setNotiType("0");
                    intent = new Intent( this , MyActivity. class );
                    break;
            }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
