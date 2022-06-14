package com.example.qr_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckCodeActivity extends AppCompatActivity {

    //넘겨받는 QR의 값
    String codeValue;

    //DB에 있는 올바른 값
    String pwdCode;

    double longitude;//경도
    double latitude;//위도
    double altitude;//고도
    float accuracy;//
    String provider;//위치제공자


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_code_main);

        setTitle("qr 값 전달 확인");

        //코드
        Intent intent = getIntent();
        codeValue = intent.getStringExtra("codeValue");

        //위치
        longitude = intent.getDoubleExtra("longitude", 0);
        latitude = intent.getDoubleExtra("latitude", 0);
        altitude = intent.getDoubleExtra("altitude", 0);
        accuracy = intent.getFloatExtra("accuracy", 0);
        provider = intent.getStringExtra("provider");

        TextView codeText = (TextView) findViewById(R.id.code);
        codeText.setText(codeValue);

        TextView locationText = (TextView) findViewById(R.id.location_code);
        locationText.setText("위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                + "\n고도 : " + altitude + "\n정확도 : "  + accuracy + "\n차이 : " + distance(latitude, longitude, 36.6276741,127.455216) +"m");


        TextView resultText = (TextView) findViewById(R.id.resultText);

        if(codeValue == pwdCode){
            if(distance(latitude, longitude, 36.6276741,127.455216) < 1000){//현재는 진행되게하기 위해서 크게 해둠.
                resultText.setText("거리가 너무 떨어져있습니다.");
            }
            else{
                resultText.setText("출석이 확인되었습니다.");
                //DB에 출석 값 넣어주는 함수
            }
        }
        else{
            resultText.setText("유효하지 않은 코드입니다");
        }

        Button backBtn = (Button) findViewById(R.id.go_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //과목 번호 넘겨주는 함수. 넘어가서 받는 함수도 써야 함.
                Intent intent = new Intent(getApplicationContext(), ScanPage.class);
                startActivity(intent);
            }
        });
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
