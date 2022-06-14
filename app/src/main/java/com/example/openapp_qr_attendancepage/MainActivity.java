package com.example.openapp_qr_attendancepage;

import androidx.activity.result.contract.ActivityResultContracts;
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

public class MainActivity extends AppCompatActivity {
    int DATE_NUM; //가져온 날짜의 개수
    TableLayout table;
    TableRow[] baseRow;
    TextView[][] baseView;

    //아래는 db의 데이터를 저장할 배열들
    String[] date; //날짜를 저장
    int[] attendance; //출석
    String[] note; //비고
    String subjectName;

    int attendNum;
    int lateNum;
    int absentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar aB = getSupportActionBar();
        aB.hide(); // 타이틀 숨기기


        //날짜의 개수
        DATE_NUM = 30; //임의로 선언

        //배열 선언
        baseRow = new TableRow[DATE_NUM];
        baseView = new TextView[DATE_NUM][4];
        date = new String[DATE_NUM];
        attendance = new int[DATE_NUM];
        note = new String[DATE_NUM];
        subjectName = "["+"객체지향 설계"+"]";
        attendNum = 3;
        lateNum = 33;
        absentNum = 20;

        //DB 배열 초기화
        for(int i=0; i<DATE_NUM; i++) //날짜
        {
            date[i] = "03/"+i;
        }
        for(int i=0; i<DATE_NUM; i++) //출석
        {
            attendance[i] = i%3-1; //1이면 출석, 0이면 지각, -1이면 결석
        }
        for(int i=0; i<DATE_NUM; i++) //비고, 공결등을 적는 곳
        {
            note[i] = " ";
        }

        //과목 이름 변경
        TextView subjectNameTV = findViewById(R.id.subjectName);
        subjectNameTV.setText(subjectName);

        //임시, 교수로 워프
        subjectNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                startActivity(intent);
            }
        });

        //출석, 지각, 결석 횟수 변경
        TextView attendNumTV = findViewById(R.id.attendNum);
        TextView lateNumTV = findViewById(R.id.lateNum);
        TextView absentNumTV = findViewById(R.id.absentNum);

        attendNumTV.setText(Integer.toString(attendNum));
        lateNumTV.setText(Integer.toString(lateNum));
        absentNumTV.setText(Integer.toString(absentNum));

        //테이블 항목 동적으로 생성
        table = findViewById(R.id.tableLayout);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        params.bottomMargin = 3;
        params.leftMargin = 3;
        params.rightMargin = 3;

        for(int i=0;i<DATE_NUM; i++) { //행 개수
            baseRow[i] = new TableRow(this);

            for (int j = 0; j < 4; j++) { //열 개수
                baseView[i][j] = new TextView(this);
                baseView[i][j].setLayoutParams(params);
                baseView[i][j].setPadding(3, 3, 3, 3);
                baseView[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                baseView[i][j].setGravity(Gravity.CENTER);
                baseView[i][j].setTextSize(15);
                switch (j){ //text를 입력
                    case 0:
                        baseView[i][j].setText(Integer.toString(i));
                        break;
                    case 1:
                        baseView[i][j].setText(date[i]);
                        break;
                    case 2:
                        switch(attendance[i]){
                            case -1:
                                baseView[i][j].setText("X");
                                baseView[i][j].setBackgroundColor(getResources().getColor(R.color.red));
                                break;
                            case 0:
                                baseView[i][j].setText("지각");
                                baseView[i][j].setBackgroundColor(getResources().getColor(R.color.yellow));
                                break;
                            case 1:
                                baseView[i][j].setText("O");
                                baseView[i][j].setBackgroundColor(getResources().getColor(R.color.skyblue));
                                break;
                        }
                        break;
                    case 3:
                        break;
                }
                baseRow[i].addView(baseView[i][j]);
            }

            table.addView(baseRow[i]);
        }
    }
}