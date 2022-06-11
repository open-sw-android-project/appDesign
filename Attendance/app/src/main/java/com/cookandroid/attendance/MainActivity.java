package com.cookandroid.attendance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int lectureNum = 0;         //수강중인 강의 개수
    LinearLayout lectureList;   //강의가 추가 될 레이아웃
    TextView tvLecture;         //수강목록 [총 n건]

    ArrayList<Button> lecBtnList = new ArrayList<Button>(); // 강의버튼 저장할 배열리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar aB = getSupportActionBar();
        aB.hide(); // 타이틀 숨기기



        lectureList = findViewById(R.id.lectureLayout);
        tvLecture = findViewById(R.id.totalLectureList);

        for (int i =0; i< 10; i++) {

        }

        tvLecture.setText("     수강목록 [총 " + lectureNum + "건]" );

    }

    public void addLecture(){ // 동적 버튼 생성, DB 받아서 반복하는 걸로 바꿔야함, 왼쪽정렬 및 디자인 교체
        Button lecture = new Button(getApplicationContext());
        lecture.setText("알기 쉬운 우리 몸");
        lecture.setMinLines(2);
        lecture.setGravity(Gravity.LEFT);
        lecture.setTextSize(20);
        lecture.setTextColor(Color.BLACK);
        lecture.setTypeface(null, Typeface.BOLD);
        lecture.setId(0);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.leftMargin = 30;
        lecture.setLayoutParams(param);

        lectureList.addView(lecture);  // 레이아웃에 강의 추가
        lectureNum++; // 강의 개수 증가
    }

}


