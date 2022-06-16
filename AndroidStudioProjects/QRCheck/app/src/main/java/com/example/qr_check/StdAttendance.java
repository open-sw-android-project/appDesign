package com.example.qr_check;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class StdAttendance extends AppCompatActivity {

    int DATE_NUM; //가져온 날짜의 개수
    TableLayout table;
    TableRow[] baseRow;
    TextView[][] baseView;


    String now_name = "이승현"; //데이터를 가져올 이름
    int now_col;
    int now_row;
    int colTotal;
    int rowTotal;
    int rowIndexStart;

    //아래는 db의 데이터를 저장할 배열들
    String[] date; //날짜를 저장
    int[] attendance; //출석
    String[] note; //비고
    String subjectName;
    int attendNum=0;
    int lateNum=0;
    int absentNum=0;

    public void readExcel() {

        String str = "운영체제_출석부";

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("운영체제_출석부.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {

                Sheet sheet = wb.getSheet(0);

                if(sheet != null) {
                    Log.d("SSS",   "SSS실행: " );
                    colTotal = sheet.getColumns();
                    rowIndexStart = 1;
                    rowTotal = sheet.getColumn(colTotal-1).length;

                    StringBuilder stringBuilder;
                    Log.d("SSS", "colTotal : "+colTotal );
                    Log.d("SSS", "rowTotal : "+rowTotal );

                    for(int row = rowIndexStart; row < rowTotal; row++) {

                        stringBuilder = new StringBuilder();

                        for(int col = 0; col < colTotal; col++) {

                            String contents = sheet.getCell(col, row).getContents();

                            if(contents.equals(now_name)){
                                Log.d("SSS",  "col : " + col + " row : "+row);
                                now_col = col;
                                now_row = row;
                                break;
                            }
                        }
                    }
                    Log.d("SSS",  "now_col : " + now_col + "now_row : "+now_row);


                }
            }
        } catch(IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    public void setExcel(){
        String str = "운영체제_출석부";

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("운영체제_출석부.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {

                Sheet sheet = wb.getSheet(0);

                if(sheet != null) {

                    Log.d("SSS",  "now_col : " + now_col + "now_row : "+now_row);
                    for(int col = 4; col<colTotal;col++){
                        int i = col-4;
                        date[i] =  sheet.getCell(col, 0).getContents();
                        attendance[i] = Integer.parseInt(sheet.getCell(col, now_row).getContents());
                        switch (attendance[i]){
                            case -1 :
                                absentNum++;
                                break;
                            case 0 :
                                lateNum++;
                                break;
                            case 1 :
                                attendNum++;
                                break;

                        }
                        Log.d("SSS", "col : "+col);
                        Log.d("SSS",  "date : " + date[i] + " attendance : "+attendance[i]);
                    }
                }
            }
        } catch(IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_attd);
        setTitle("출석 현황");

        ActionBar aB = getSupportActionBar();
        aB.hide(); // 타이틀 숨기기

        readExcel();


        //날짜의 개수
        DATE_NUM = colTotal-4; //임의로 선언

        //배열 선언
        baseRow = new TableRow[DATE_NUM];
        baseView = new TextView[DATE_NUM][4];
        date = new String[DATE_NUM];
        attendance = new int[DATE_NUM];
        note = new String[DATE_NUM];
        subjectName = "["+"객체지향 설계"+"]";

        setExcel();
//        //DB 배열 초기화
//        for(int i=0; i<DATE_NUM; i++) //날짜
//        {
//            date[i] = "03/"+i;
//        }
//        for(int i=0; i<DATE_NUM; i++) //출석
//        {
//            attendance[i] = i%3-1; //1이면 출석, 0이면 지각, -1이면 결석
//        }
//        for(int i=0; i<DATE_NUM; i++) //비고, 공결등을 적는 곳
//        {
//            note[i] = " ";
//        }

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
                        baseView[i][j].setText(Integer.toString(i+1));
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
                                baseView[i][j].setText("L");
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