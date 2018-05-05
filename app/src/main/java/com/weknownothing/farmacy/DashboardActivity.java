package com.weknownothing.farmacy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.weknownothing.farmacy.Functionalities.DetectDiseaseActivity;
import com.weknownothing.farmacy.Functionalities.WeatherForecastActivity;
import com.weknownothing.farmacy.Services.AlertService;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.imageView_detect_disease:
                startActivity(new Intent(this, DetectDiseaseActivity.class));
                break;

            case R.id.imageView_crop_info:
                break;

            case R.id.imageView_crop_suggestion:
                break;

            case R.id.imageView_weather_forecast:
                startActivity(new Intent(this, WeatherForecastActivity.class));
                break;

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(!isMyServiceRunning(AlertService.class))
            startService(new Intent(DashboardActivity.this , AlertService.class));

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }


}
