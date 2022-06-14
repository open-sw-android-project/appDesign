package com.example.openapp_qr_attendancepage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SubActivity extends AppCompatActivity {
    int STU_NUM; //가져온 학생의 숫자
    int DATE_NUM; //가져온 날짜의 숫자
    TableLayout left_table;
    TableLayout right_table;
    TableRow[] baseRow_left;
    TextView[][] baseView_left;
    TableRow[] baseRow_right;
    TextView[][] baseView_right;

    //아래는 db의 데이터를 저장할 배열들
    String[] date; //날짜를 저장
    String[] studentID; //학번
    String[] studentName; //이름
    int[][] attendance; //출석
    String subjectName; //과목 이름

    int attendNum;
    int lateNum;
    int absentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subtivity_main);
        ActionBar aB = getSupportActionBar();
        aB.hide(); // 타이틀 숨기기


        //학생의 숫자
        STU_NUM = 30;

        //배열 선언
        baseRow_left = new TableRow[STU_NUM];
        baseView_left = new TextView[STU_NUM][3];
        baseRow_right = new TableRow[STU_NUM];
        baseView_right = new TextView[STU_NUM][DATE_NUM];

        studentID = new String[STU_NUM];
        studentName = new String[STU_NUM];
        date = new String[DATE_NUM];
        attendance = new int[STU_NUM][DATE_NUM];
        subjectName = "["+"객체지향 설계"+"]";
        attendNum = 3;
        lateNum = 33;
        absentNum = 20;

        //DB 배열 초기화
        for(int i=0; i<STU_NUM; i++) //날짜
        {
            date[i] = "03/"+i;
        }
        //출석 정보 입력
        for(int i=0; i<STU_NUM; i++) //학생의 숫자
        {
            for(int j=0; j<DATE_NUM; j++){ //날짜의 개수
                //학생번째의 날짜(앞에서부터(
                attendance[i][j] = j%3-1; //1이면 출석, 0이면 지각, -1이면 결석
            }
        }

        //과목 이름 변경
        TextView subjectNameTV = findViewById(R.id.subjectName);
        subjectNameTV.setText(subjectName);


        //출석, 지각, 결석 횟수 변경
        TextView attendNumTV = findViewById(R.id.attendNum);
        TextView lateNumTV = findViewById(R.id.lateNum);
        TextView absentNumTV = findViewById(R.id.absentNum);

        attendNumTV.setText(Integer.toString(attendNum));
        lateNumTV.setText(Integer.toString(lateNum));
        absentNumTV.setText(Integer.toString(absentNum));

        //테이블 항목 동적으로 생성
        left_table = findViewById(R.id.tableLayoutLeft);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        params.bottomMargin = 3;
        params.leftMargin = 3;
        params.rightMargin = 3;

        for(int i=0;i<STU_NUM; i++) { //행의 개수
            baseRow_left[i] = new TableRow(this);

            for (int j = 0; j < 3; j++) { //열의 개수
                baseView_left[i][j] = new TextView(this);
                baseView_left[i][j].setLayoutParams(params);
                baseView_left[i][j].setPadding(3, 3, 3, 3);
                baseView_left[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                baseView_left[i][j].setGravity(Gravity.CENTER);
                baseView_left[i][j].setTextSize(15);
                switch (j){ //text를 입력
                    case 0:
                        baseView_left[i][j].setText(Integer.toString(i));
                        break;
                    case 1:
                        baseView_left[i][j].setText(date[i]);
                        break;
                    case 2:
                        baseView_left[i][j].setText(date[i]);
                        break;
                }
                baseRow_left[i].addView(baseView_left[i][j]);
            }

            left_table.addView(baseRow_left[i]);
        }
    }
}