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
    String crop_name;
    String gen_info;
    String soil_info;
    TextView crop_name_text , crop_ginfo_text , crop_sinfo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        Intent i = getIntent();
        crop = i.getStringExtra("MSG").replace(" ", "");

        crop_name_text = (TextView)findViewById(R.id.crop_name_text);
        crop_ginfo_text = (TextView)findViewById(R.id.crop_ginfo_text);
        crop_sinfo_text = (TextView)findViewById(R.id.crop_sinfo_text);


        onReadCSV();
    }

    private void onReadCSV() {

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
                    crop_name = nextline[1];
                    gen_info = nextline[2];
                    soil_info = nextline[3];
                    break;
                }

            }

            crop_name_text.setText(crop_name);
            crop_name_text.setText(gen_info);
            crop_name_text.setText(soil_info);



        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
