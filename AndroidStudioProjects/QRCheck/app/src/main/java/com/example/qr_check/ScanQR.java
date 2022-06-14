package com.example.qr_check;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;

public class ScanQR extends AppCompatActivity{
    //객체 담을 변수 선언
    private CodeScanner mCodeScanner;
    int cameraCount = 0;
    String SubjectCode;

    //위치 정보 받을 변수들
    double longitude=0;//경도
    double latitude=0;//위도
    double altitude=0;//고도
    float accuracy=0;//
    String provider;//위치제공자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        //타이틀 선언
        setTitle("QR 스캔");

        //변수 가져오기
        Intent intent = getIntent();
        String SubjectName = intent.getStringExtra("course_name");

        //qr 카메라 담을 view xml내부 요소 가져오기
        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        //qr scanner 객체 생성
        mCodeScanner = new CodeScanner(this, scannerView);

        // LocationManager 객체를 얻어온다
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //위치 정보 함수
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScanQR.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            System.out.println("\n\n\nno location\n\n\n");
        }
        else{
            try {
                // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
//                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
//                        100, // 통지사이의 최소 시간간격 (miliSecond)
//                        1, // 통지사이의 최소 변경거리 (m)
//                        mLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                        100, // 통지사이의 최소 시간간격 (miliSecond)
                        1, // 통지사이의 최소 변경거리 (m)
                        mLocationListener);
//                                lm.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.
                System.out.println("\n\n\nyes location\n\n\n");

                System.out.println("long" + longitude);
            } catch (SecurityException ex) {
            }
        }

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                //NonNull 어노테이션은 자동으로 null인지 확인한다. Null이면 exeption을 일으켜줌.
                //result 값이 decode한 값이다.
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        //Toast.makeText(ScanPage.this, result.getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CheckCodeActivity.class);
                        intent.putExtra("codeValue", result.getText());


                        intent.putExtra("longitude", longitude);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("altitude", altitude);
                        intent.putExtra("accuracy", accuracy);
                        intent.putExtra("provider", provider);

                        lm.removeUpdates(mLocationListener);

                        startActivity(intent);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        Button changeCamera = (Button) findViewById(R.id.changeCamera);
        changeCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(cameraCount == 0){
                    cameraCount = 1;
                }
                else{
                    cameraCount = 0;
                }
                mCodeScanner.setCamera(cameraCount);
            }
        });

        TextView subName = (TextView) findViewById(R.id.subName);
        subName.setText("현재 출석 진행중인 수업: " + SubjectName);

        Button attendBtn = (Button) findViewById(R.id.toAttend);
        attendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //과목 번호 넘겨주는 함수. 넘어가서 받는 함수도 써야 함.
                Intent intent = new Intent(getApplicationContext(), StdAttendance.class);
                intent.putExtra("course_name", SubjectName);

                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private final LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            longitude = location.getLongitude(); //경도
            latitude = location.getLatitude();   //위도
            altitude = location.getAltitude();   //고도
            accuracy = location.getAccuracy();    //정확도
            provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
//            System.out.println("\n\n\n위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
//                    + "\n고도 : " + altitude + "\n정확도 : "  + accuracy + "\n차이 : " + distance(latitude, longitude, 36.6276741,127.455216) +"m");

        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
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
    };

}