package com.weknownothing.farmacy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
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
import com.weknownothing.farmacy.Functionalities.DetectDiseaseActivity;
import com.weknownothing.farmacy.Functionalities.DiseaseInfoActivity;
import com.weknownothing.farmacy.Functionalities.WeatherForecastActivity;
import com.weknownothing.farmacy.Services.AlertService;
import com.weknownothing.farmacy.Utilities.MySingelton;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "IMAGE UPLOADING";
    private Bitmap mImageBitmap;
    public static final String Uploadurl = "https://55c88448.ngrok.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
                break;

            case R.id.imageView_crop_suggestion:
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
            Toast.makeText(this, "Bitmap received", Toast.LENGTH_SHORT).show();

            UploadImage upld = new UploadImage();
            upld.execute();
//            mImageView.setImageBitmap(imageBitmap);
            Toast.makeText(this, "CLassified", Toast.LENGTH_SHORT).show();

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
