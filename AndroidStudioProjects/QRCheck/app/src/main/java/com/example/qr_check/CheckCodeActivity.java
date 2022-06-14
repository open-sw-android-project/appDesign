package com.example.qr_check;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckCodeActivity extends AppCompatActivity {

    String codeValue;

    double longitude;//경도
    double latitude;//위도
    double altitude;//고도
    float accuracy;//
    String provider;//위치제공자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        setTitle("qr 값 전달 확인");

        //코드
        Intent intent = getIntent();
        codeValue = intent.getStringExtra("codeValue");

        //위치
        longitude = intent.getDoubleExtra("longitude", 10);
        latitude = intent.getDoubleExtra("latitude", 10);
        altitude = intent.getDoubleExtra("altitude", 10);
        accuracy = intent.getFloatExtra("accuracy", 10);
        provider = intent.getStringExtra("provider");

        TextView codeText = (TextView) findViewById(R.id.code);
        codeText.setText(codeValue);

        TextView locationText = (TextView) findViewById(R.id.location_code);
        locationText.setText("위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                + "\n고도 : " + altitude + "\n정확도 : "  + accuracy + "\n차이 : " + distance(latitude, longitude, 36.6276741,127.455216) +"m");
    }

    private double distance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return Math.floor(dist); //단위 meter
    }
    private double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    private double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }
}
