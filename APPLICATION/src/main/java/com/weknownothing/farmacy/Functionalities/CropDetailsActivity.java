package com.weknownothing.farmacy.Functionalities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
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

    CardView crop_name_cv , crop_ginfo_cv, crop_sinfo_cv;

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

        crop_name_cv = (CardView)findViewById(R.id.crop_name_cv);
        crop_ginfo_cv = (CardView)findViewById(R.id.crop_ginfo_cv);
        crop_sinfo_cv = (CardView)findViewById(R.id.crop_sinfo_cv);



        onReadCSV();
    }

    private void onReadCSV() {

        try {

            String csvFile = this.getApplicationInfo().dataDir + File.separatorChar + "cropinfo.csv";

            InputStreamReader inputStreamReader = new InputStreamReader(getResources().openRawResource(R.raw.cropinfo));

            CSVReader csvReader = new CSVReader(inputStreamReader, '|');
            String[] nextline;

            while ((nextline = csvReader.readNext()) != null)
            {
                String ss = nextline[0].replace(" ", "");
                if(ss.equalsIgnoreCase(crop))
                {

                    crop_name = nextline[0];
                    gen_info = nextline[1];
                    soil_info = nextline[2];
                    break;
                }

            }

            crop_name = crop_name.replaceAll("\\s+", "");
            if(!crop_name.replace(" ","").equalsIgnoreCase(""))
            {
                crop_name_cv.setVisibility(View.VISIBLE);
                crop_name_text.setText(crop_name);
            }
            else
                crop_name_cv.setVisibility(View.GONE);

            gen_info = gen_info.replaceAll("\\s+", "");
            if(!gen_info.replace(" ","").equalsIgnoreCase(""))
            {
                crop_ginfo_cv.setVisibility(View.VISIBLE);
                crop_ginfo_text.setText(gen_info);
            }
            else
                crop_ginfo_cv.setVisibility(View.GONE);

            soil_info = soil_info.replaceAll("\\s+", "");
            if(!soil_info.equals(""))
            {
                crop_sinfo_cv.setVisibility(View.VISIBLE);
                crop_sinfo_text.setText(soil_info);
            }
            else
                crop_sinfo_cv.setVisibility(View.GONE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
