package com.weknownothing.farmacy.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.weknownothing.farmacy.Api.RestAPI;
import com.weknownothing.farmacy.Models.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertService extends Service {

    private static final String TAG = "AlertService";

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
                Log.e(TAG, "Running in every 10 seconds");
                startTasK();
            }
        }, 0, 10, TimeUnit.SECONDS);
        return START_STICKY;


    }

    private void startTasK() {

        RestAPI.getAppService().getData().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent restart = new Intent(getApplicationContext(), this.getClass());
        restart.setPackage(getPackageName());
        startService(restart);
    }
}
