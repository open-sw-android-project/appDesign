package com.example.openapp_qr_attendancepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int MAX_Num; //가져온 데이터의 최댓값
    TableLayout table;
    TableRow[] baseRow;
    TextView[][] baseView;

    //아래는 db의 데이터를 저장할 배열들
    String[] date; //날짜를 저장
    int[] attendance; //출석
    String[] note; //비고
    String subjectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터의 개수
        MAX_Num = 30;

        //배열 선언
        baseRow = new TableRow[MAX_Num];
        baseView = new TextView[MAX_Num][4];
        date = new String[MAX_Num];
        attendance = new int[MAX_Num];
        note = new String[MAX_Num];
        subjectName = "["+"객체지향 설계"+"]";

        //DB 배열 초기화
        for(int i=0; i<MAX_Num; i++) //날짜
        {
            date[i] = "03/"+i;
        }
        for(int i=0; i<MAX_Num; i++) //출석
        {
            attendance[i] = i%3-1; //1이면 출석, 0이면 지각, -1이면 결석
        }
        for(int i=0; i<MAX_Num; i++) //비고, 공결등을 적는 곳
        {
            note[i] = " ";
        }
        //과목 이름 변경
        TextView subjectNameT = findViewById(R.id.subjectName);
        subjectNameT.setText(subjectName);

        //테이블 항목 동적으로 생성
        table = findViewById(R.id.tableLayout);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        params.topMargin = 3;
        params.bottomMargin = 3;
        params.leftMargin = 3;
        params.rightMargin = 3;

        for(int i=0;i<30; i++) {
            baseRow[i] = new TableRow(this);

            for (int j = 0; j < 4; j++) {
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