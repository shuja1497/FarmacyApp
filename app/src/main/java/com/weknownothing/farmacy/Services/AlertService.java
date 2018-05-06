package com.weknownothing.farmacy.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.weknownothing.farmacy.Api.Request.TestSet_Model;
import com.weknownothing.farmacy.Api.Response.AlertResponse;
import com.weknownothing.farmacy.Api.RestAPI;
import com.weknownothing.farmacy.Api.RestAPIServer;
import com.weknownothing.farmacy.Models.Data;
import com.weknownothing.farmacy.Utilities.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertService extends Service {

    private static final String TAG = "AlertService";
    private TestSet_Model testSet_model;

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
        }, 0, Constants.TIME_INTERVAL, TimeUnit.SECONDS);
        return START_STICKY;


    }

    private void startTasK() {
        testSet_model = new TestSet_Model();
        RestAPI.getAppService().getData().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "onResponse: "+response.body().toString());
                setTestSetModel(response);
                sendDataToServer();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }

    private void sendDataToServer() {
        Log.e(TAG, "sendDataToServer: Sending "+testSet_model.toString());

        RestAPIServer.getAppService().getAlertStatus(testSet_model).enqueue(new Callback<AlertResponse>() {
            @Override
            public void onResponse(Call<AlertResponse> call, Response<AlertResponse> response) {
                Log.e(TAG, "onResponse: "+response.body().getDay1() );
            }

            @Override
            public void onFailure(Call<AlertResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });


    }

    private void setTestSetModel(Response<Data> response) {

        int i =0;

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

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent restart = new Intent(getApplicationContext(), this.getClass());
        restart.setPackage(getPackageName());
        startService(restart);
    }
}
