package com.weknownothing.farmacy;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.weknownothing.farmacy.Functionalities.CropSearchActivity;
import com.weknownothing.farmacy.Functionalities.CropSuggestionActivity;
import com.weknownothing.farmacy.Functionalities.DetectDiseaseActivity;
import com.weknownothing.farmacy.Functionalities.DiseaseInfoActivity;
import com.weknownothing.farmacy.Functionalities.WeatherForecastActivity;
import com.weknownothing.farmacy.Services.AlertService;
import com.weknownothing.farmacy.Utilities.Constants;
import com.weknownothing.farmacy.Utilities.MySingelton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class DashboardActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int SEND_SMS = 2;
    private static final String TAG = "IMAGE UPLOADING";
    private Bitmap mImageBitmap;
    public static final String Uploadurl = Constants.BASE_URL_SERVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getPermission();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int alert = intent.getIntExtra("alert",-1);
            Log.e(TAG, "Broadcast from ALertServices" +alert);
        }
    };

    private void getPermission() {

        //checking for permission to SEND_SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS);
            }
        }
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.imageView_detect_disease:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
//                startActivity(new Intent(this, DetectDiseaseActivity.class));
                break;

            case R.id.imageView_crop_info:
                startActivity(new Intent(this, CropSearchActivity.class));
                break;

            case R.id.imageView_crop_suggestion:
                startActivity(new Intent(this, CropSuggestionActivity.class));
                break;

            case R.id.imageView_weather_forecast:
                startActivity(new Intent(this, WeatherForecastActivity.class));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mImageBitmap = (Bitmap) extras.get("data");
//            Toast.makeText(this, "Bitmap received", Toast.LENGTH_SHORT).show();

            UploadImage upld = new UploadImage();
            upld.execute();
//            mImageView.setImageBitmap(imageBitmap);
//            Toast.makeText(this, "CLassified", Toast.LENGTH_SHORT).show();

        }
    }


    private class UploadImage extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
//            String.format(Uploadurl,ImagetoString(bitmap));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Uploadurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "*******onResponse: "+response);

//                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), DiseaseInfoActivity.class);
                            i.putExtra("index", response);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: "+ error);

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params =new HashMap<>();
                    params.put("image",ImagetoString(mImageBitmap));

                    return params;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);

            MySingelton.getInstance(DashboardActivity.this).addToRequestQue(stringRequest);

            return null;
        }
    }

    private String ImagetoString(Bitmap bitmap)
    {
        Log.d(TAG, "ImagetoString: in");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        Log.d(TAG, "ImagetoString: out*******"+imgBytes.length);
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("ALERT"));
        if(!isMyServiceRunning(AlertService.class))
            startService(new Intent(DashboardActivity.this , AlertService.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        //registering the receiver again on resume
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("ALERT"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //deregistering the receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
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
