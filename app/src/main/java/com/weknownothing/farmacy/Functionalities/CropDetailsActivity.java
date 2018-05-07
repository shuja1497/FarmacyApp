package com.weknownothing.farmacy.Functionalities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.weknownothing.farmacy.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CropDetailsActivity extends AppCompatActivity {


    String crop;
    TextView cropText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        //cropText = (TextView)findViewById(R.id.cropText);
        Intent i = getIntent();

        crop = i.getStringExtra("MSG").replace(" ", "");


        onReadCSV();
    }

    private void onReadCSV() {
        ArrayList name = new ArrayList();
        ArrayList ginfo = new ArrayList();
        ArrayList sinfo = new ArrayList();

        try {

            String csvFile = this.getApplicationInfo().dataDir + File.separatorChar + "info.csv";

            Toast.makeText(getApplicationContext(), csvFile, Toast.LENGTH_LONG).show();

            InputStreamReader inputStreamReader = new InputStreamReader(getResources().openRawResource(R.raw.info));

            CSVReader csvReader = new CSVReader(inputStreamReader, '|');
            String[] nextline;

            while ((nextline = csvReader.readNext()) != null)
            {
                String ss = nextline[0].replace(" ", "");
                if(ss.equalsIgnoreCase(crop))
                {
                    String crop_name = nextline[1];
                    String gen_info = nextline[2];
                    String soil_info = nextline[3];


                    break;
                }

            }



        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
