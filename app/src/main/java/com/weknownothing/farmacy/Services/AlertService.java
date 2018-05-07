package com.weknownothing.farmacy.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.weknownothing.farmacy.Api.Request.TestSet_Model;
import com.weknownothing.farmacy.Api.Response.AlertResponse;
import com.weknownothing.farmacy.Api.RestAPI;
import com.weknownothing.farmacy.Api.RestAPIServer;
import com.weknownothing.farmacy.DashboardActivity;
import com.weknownothing.farmacy.Models.Data;
import com.weknownothing.farmacy.R;
import com.weknownothing.farmacy.Utilities.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertService extends Service {

    private static final String TAG = "AlertService";
    public static final int NOTIFICATION_ID = 99;
    private TestSet_Model testSet_model;
    private String[] contactNumbers;
    private String phoneNumbers = "";

    public AlertService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        // This schedule a runnable task every 10 sec
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.e(TAG, "Running in every " + Constants.TIME_INTERVAL + " seconds");
                startTasK();
            }
        }, 0, Constants.TIME_INTERVAL, TimeUnit.SECONDS);
        return START_STICKY;


    }

    private void startTasK() {

        testSet_model = new TestSet_Model();
        RestAPI.getAppService().getData().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                setTestSetModel(response);
                sendDataToServer();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void sendDataToServer() {
        Log.e(TAG, "sendDataToServer: Sending " + testSet_model.toString());

        RestAPIServer.getAppService().getAlertStatus(testSet_model).enqueue(new Callback<AlertResponse>() {
            @Override
            public void onResponse(Call<AlertResponse> call, Response<AlertResponse> response) {
                try {
                    //Log.e(TAG, "onResponse: " + response.body().getDay1());
                    if (response.body().getDay1() == 1)
                        sendSMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AlertResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    private void setTestSetModel(Response<Data> response) {

        int i = 0;

        String date[] = response.body().getDays().get(i).getDate().split("/");

        testSet_model.setMon(Integer.parseInt(date[1]));
        testSet_model.setMday(Integer.parseInt(date[0]));

        testSet_model.setMaxhumidity((int) response.body().getDays().get(i).getHumid_max_pct());
        testSet_model.setMinhumidity((int) response.body().getDays().get(i).getHumid_min_pct());
        testSet_model.setMaxpressurem((int) response.body().getDays().get(i).getTemp_max_c());
        testSet_model.setMaxtempm((int) response.body().getDays().get(i).getTemp_max_c());
        testSet_model.setMintempm((int) response.body().getDays().get(i).getTemp_min_c());
        testSet_model.setMaxpressurem((int) response.body().getDays().get(i).getSlp_max_mb());
        testSet_model.setMinpressurem((int) response.body().getDays().get(i).getSlp_min_mb());
        testSet_model.setMaxwspdm((int) response.body().getDays().get(i).getWindspd_max_ms());

        testSet_model.setMeanpressurem();
        testSet_model.setMeantempm();
        testSet_model.setHumidity();
    }

    private void sendSMS() {

        foreground();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {

//                Toast.makeText(getApplicationContext(),"ALERT Rain !",Toast.LENGTH_SHORT).show();
                try {
//                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                    vibe.vibrate(Constants.VIBRATION_DURATION);
//                    sendMessageToActivity();
                    for (int i = 0; i < contactNumbers.length; i++) {
                        SmsManager smn = SmsManager.getDefault();
                        Log.e(TAG, "sendSMS: SMS sending" + contactNumbers[i]);
                        smn.sendTextMessage(contactNumbers[i], null, getApplicationContext().getResources().getString(R.string.alertMessage), null, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void foreground() {
        startForeground(NOTIFICATION_ID, createNotification());
    }

    private Notification createNotification() {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Farmacy ALERT !")
                .setContentText("There are strong chances of Rain tommorow")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        Intent resultIntent = new Intent(this, DashboardActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        return builder.build();
    }

    private void sendMessageToActivity() {
        Intent intent = new Intent("ALERT");
        intent.putExtra("alert", 1);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent restart = new Intent(getApplicationContext(), this.getClass());
        restart.setPackage(getPackageName());
        startService(restart);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contactNumbers = getResources().getStringArray(R.array.Contact_Number_for_SMS);

    }
}
