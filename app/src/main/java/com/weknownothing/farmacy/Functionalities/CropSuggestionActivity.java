package com.weknownothing.farmacy.Functionalities;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weknownothing.farmacy.Api.Api;
import com.weknownothing.farmacy.Api.Response.Responsee;
import com.weknownothing.farmacy.Models.CropInfo;
import com.weknownothing.farmacy.Models.Data;
import com.weknownothing.farmacy.Models.Days;
import com.weknownothing.farmacy.Models.ListItem;
import com.weknownothing.farmacy.R;
import com.weknownothing.farmacy.Utilities.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CropSuggestionActivity extends AppCompatActivity {

    private List<Days> daysList;
    ArrayList<Days> dlist;
    private List<ListItem> listItems;
    private Context context = this;
    private List<CropInfo> cropInfos = new ArrayList<>();

    ListView listView;

    String t_max;
    String t_min;
    String precip;
    String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_suggestion);
        daysList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list);
        listItems = new ArrayList<>();

        getData();
    }


    private void getData() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<Data> call = api.getData();


        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //Log.i("Response", "onNet: "+  response.toString());
                dlist = response.body().getDays();

                Log.i("Dlist", "onResponse: " + dlist);
                for (int i = 0; i < dlist.size(); i++) {

                    Days days = new Days(dlist.get(i).getDate(),
                            dlist.get(i).getTemp_min_c(), dlist.get(i).getTemp_max_c()
                            , dlist.get(i).getHumid_min_pct(), dlist.get(i).getHumid_max_pct(),
                            dlist.get(i).getPrecip_total_mm());

                    daysList.add(days);
                }

                Days days = new Days(dlist.get(0).getDate(),
                        dlist.get(0).getTemp_min_c(), dlist.get(0).getTemp_max_c()
                        , dlist.get(0).getHumid_min_pct(), dlist.get(0).getHumid_max_pct(),
                        dlist.get(0).getPrecip_total_mm());

                Calendar calendar = Calendar.getInstance();
                int thisMonth = calendar.get(Calendar.MONTH);
                thisMonth = thisMonth + 1;
                t_max = String.valueOf(dlist.get(0).getTemp_max_c());
                t_min = String.valueOf(dlist.get(0).getTemp_min_c());
                precip = String.valueOf(dlist.get(0).getPrecip_total_mm());
                month = String.valueOf(thisMonth);

                //Log.d("Month", "@ thisMonth : " + thisMonth);

                getCrop(month, t_min, t_max, precip);


            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getCrop(String month, String t_min, String t_max, String precip) {
        Gson gson1 = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://460b6ebc.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api1 = retrofit.create(Api.class);

        final Call<Responsee> call_data = api1.getDate(month, t_min, t_max, precip);

        call_data.enqueue(new Callback<Responsee>() {
            @Override
            public void onResponse(Call<Responsee> call, Response<Responsee> response) {
                final String[] arr = {response.body().getCrop1(), response.body().getCrop2(), response.body().getCrop3(), response.body().getCrop4(), response.body().getCrop5()};

                for (int i = 0; i < 5; i++) {
                    ListItem listItem = new ListItem(arr[i]);
                    listItems.add(listItem);
                }

                Toast.makeText(getApplicationContext(), response.body().getCrop1() + response.body().getCrop2() +
                        response.body().getCrop3() + response.body().getCrop4() + response.body().getCrop5(), Toast.LENGTH_LONG).show();

                getCropInfo(response);
            }

            @Override
            public void onFailure(Call<Responsee> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getCropInfo(final Response<Responsee> response)

    {
        final String[] arr = {response.body().getCrop1(), response.body().getCrop2(), response.body().getCrop3(), response.body().getCrop4(), response.body().getCrop5()};
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, arr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arr[position].equalsIgnoreCase(response.body().getCrop1())) {
                    Intent i1 = new Intent(context, CropDetailsActivity.class);
                    i1.putExtra("MSG", arr[position]);
                    startActivity(i1);
                    Toast.makeText(getApplicationContext(), "crop1", Toast.LENGTH_LONG).show();
                }

                if (arr[position].equalsIgnoreCase(response.body().getCrop2())) {
                    Intent i2 = new Intent(context, CropDetailsActivity.class);
                    i2.putExtra("MSG", arr[position]);
                    startActivity(i2);
                    Toast.makeText(getApplicationContext(), "crop2", Toast.LENGTH_LONG).show();
                }
                if (arr[position].equalsIgnoreCase(response.body().getCrop3())) {
                    Intent i3 = new Intent(context, CropDetailsActivity.class);
                    i3.putExtra("MSG", arr[position]);
                    startActivity(i3);
                    Toast.makeText(getApplicationContext(), "crop3", Toast.LENGTH_LONG).show();
                }
                if (arr[position].equalsIgnoreCase(response.body().getCrop4())) {
                    Intent i4 = new Intent(context, CropDetailsActivity.class);
                    i4.putExtra("MSG", arr[position]);
                    startActivity(i4);
                    Toast.makeText(getApplicationContext(), "crop4", Toast.LENGTH_LONG).show();
                }
                if (arr[position].equalsIgnoreCase(response.body().getCrop5())) {
                    Intent i5 = new Intent(context, CropDetailsActivity.class);
                    i5.putExtra("MSG", arr[position]);
                    startActivity(i5);
                    Toast.makeText(getApplicationContext(), "crop5", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
