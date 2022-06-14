package com.cookandroid.atten_csv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.atten_test)); // rawdata 읽기
        BufferedReader reader = new BufferedReader(is); // 버퍼리더에 읽은 데이터 집어넣기

        CSVReader read = new CSVReader(reader); // csv리더에 데이터 집어넣기

        ArrayList<String[]> dataList = new ArrayList<>(); // 읽은 데이터 저장할 ArrayList

        try{
            String[] line; // 줄별로 저장
            while((line= read.readNext()) != null){ // 쭉 저장
                dataList.add(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }catch (CsvValidationException c) {
            c.printStackTrace();
        }


        /**
         * read
         * */

        for (String[] data : dataList) {
            Log.d("th_csv_test2", "data : " + Arrays.deepToString(data));
        }

        JSONObject obj = new JSONObject();


    }


}


