package com.example.qr_check;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    List<CodeCheck> list_get;
    List<CodeCheck> list_put;
    Integer result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        setTitle("qr 값 전달 확인");

        Intent intent = getIntent();
        codeValue = intent.getStringExtra("codeValue");
        String std_id = intent.getStringExtra("std_id");
        String course_name = intent.getStringExtra("course_name");

        TextView resultText = (TextView) findViewById(R.id.resultText);

        list_get = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.151/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SeverInterface apiInterface = retrofit.create(SeverInterface.class);
        apiInterface.getCode(course_name).enqueue(new Callback<List<CodeCheck>>() {
            @Override
            public void onResponse(Call<List<CodeCheck>> call, Response<List<CodeCheck>> response) {
                if(response.isSuccessful()) {
                    list_get = response.body();
                    pwdCode = list_get.get(0).getCode();
                }
            }

            @Override
            public void onFailure(Call<List<CodeCheck>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        if(codeValue.equals(pwdCode) || pwdCode == null){
            if(distance(latitude, longitude, 36.6276741,127.455216) < 1000){//현재는 진행되게하기 위해서 크게 해둠.
                resultText.setText("거리가 너무 떨어져있습니다.");
            }
            else{
                resultText.setText("출석이 확인되었습니다.");
                result = 1;
                //DB에 출석 값 넣어주는 함수
            }
        }
        else{
            resultText.setText("유효하지 않은 코드입니다");
            result = 2;

            System.out.println(pwdCode);
        }

        SeverInterface severInterface = retrofit.create(SeverInterface.class);
        severInterface.putCodeInfo(std_id, course_name, result).enqueue(new Callback<List<CodeCheck>>() {
            @Override
            public void onResponse(Call<List<CodeCheck>> call, Response<List<CodeCheck>> response) {
                if(response.isSuccessful()) {
                    System.out.println("성공");
                }
            }

            @Override
            public void onFailure(Call<List<CodeCheck>> call, Throwable t) {
                t.printStackTrace();
                System.out.println("실패");
            }
        });

        //코드

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



        Button backBtn = (Button) findViewById(R.id.go_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //과목 번호 넘겨주는 함수. 넘어가서 받는 함수도 써야 함.
                Intent intent = new Intent(getApplicationContext(), ScanQR.class);
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


    public class CodeCheck {

        @SerializedName("std_id")
        private String std_id;

        @SerializedName("course_name")
        private String course_name;

        @SerializedName("result")
        private Integer result;

        @SerializedName("code")
        private String code;

        public String getStd_id() {
            return std_id;
        }

        public void setStd_id(String std_id) {
            this.std_id = std_id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}