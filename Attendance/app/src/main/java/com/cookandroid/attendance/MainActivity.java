package com.cookandroid.attendance;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int lectureNum = 0;         //수강중인 강의 개수
    LinearLayout lectureList;   //강의가 추가 될 레이아웃
    TextView tvLecture;         //수강목록 [총 n건]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar aB = getSupportActionBar();
        aB.hide(); // 타이틀 숨기기

        lectureList = findViewById(R.id.lectureLayout);
        tvLecture = findViewById(R.id.totalLectureList);


        addLecture("컴파일러");
        addLecture("자료구조");
        addLecture("오픈소스전문프로젝트");
        addLecture("컴퓨터네트워크");
        addLecture("창업기획");

        tvLecture.setText("     수강목록 [총 " + lectureNum + "건]" ); // 수강목록 텍스트 set

    }

    public void addLecture(String LectureName){ // 동적 버튼 생성, DB 받아서 반복하는 걸로 바꿔야함, 왼쪽정렬 및 디자인 교체
        Button lecture = new Button(getApplicationContext());
        lecture.setText(LectureName);
        lecture.setMinLines(2);
        lecture.setGravity(Gravity.LEFT);
        lecture.setTextSize(20);
        lecture.setTextColor(Color.WHITE);
        lecture.setTypeface(null, Typeface.BOLD);
        lecture.setId(lectureNum++);
        lecture.setPadding(10, 10 ,10 , 10);
        lecture.setBackground(getResources().getDrawable(R.drawable.btn_2));
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.leftMargin = 30;
        param.bottomMargin = 10;
        lecture.setLayoutParams(param);

        lecture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("Btn: ", LectureName );
            }

        });

        lectureList.addView(lecture);  // 레이아웃에 강의 추가
         // 강의 개수 증가
    }

}


