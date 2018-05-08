package com.weknownothing.farmacy.Functionalities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.weknownothing.farmacy.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CropSearchActivity extends AppCompatActivity
{
    private ListView list;
//    EditText ed1;

    //ListView Adapter
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_search);

        final ArrayList name = new ArrayList();

        try {

            String csvFile = this.getApplicationInfo().dataDir + File.separatorChar + "cropinfo.csv";

            InputStreamReader inputStreamReader = new InputStreamReader(getResources().openRawResource(R.raw.cropinfo));

            CSVReader csvReader = new CSVReader(inputStreamReader, '|');
            String[] nextline;

            while ((nextline = csvReader.readNext()) != null)
            {
                nextline[0].replace(" ", "");

                if(nextline[0].equals("CROP NAME"))
                {

                }

                else
                    name.add(nextline[0]);

            }

        }

        catch (IOException e) {
            e.printStackTrace();
        }


        list = (ListView)findViewById(R.id.list);

//        ed1 = (EditText)findViewById(R.id.ed1);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent i1 = new Intent(view.getContext(), CropDetailsActivity.class);
                i1.putExtra("MSG", name.get(position).toString());
                startActivity(i1);
            }
        });

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_crop_search, R.id.txt1, name);
        list.setAdapter(adapter);

    }
}
