package com.example.qr_check;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProAttendance extends AppCompatActivity {

    int STU_NUM; //가져온 학생의 숫자
    int DATE_NUM; //가져온 날짜의 숫자
    TableLayout left_table;
    TableLayout right_table;
    TableRow[] baseRow_left;
    TextView[][] baseView_left;
    TableRow[] baseRow_right;
    TextView[][] baseView_right;

    int colTotal;
    int rowTotal;
    int rowIndexStart;

    //아래는 db의 데이터를 저장할 배열들
    String[] date; //날짜를 저장
    String[] studentID; //학번
    String[] studentName; //이름
    int[][] attendance; //출석
    String subjectName; //과목 이름

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

                    for(int row=1; row<rowTotal; row++) {
                            studentID[row-1] = sheet.getCell(1, row).getContents();
                            studentName[row-1] = sheet.getCell(3, row).getContents();
                    }
                    for (int col = 4; col < colTotal; col++) {
                        int i = col - 4;
                        date[i] = sheet.getCell(col, 0).getContents();
                    }
                    for(int row=1; row<rowTotal; row++){
                        for(int col = 4; col<colTotal; col++){
                            attendance[row-1][col-4] = Integer.parseInt(sheet.getCell(col, row).getContents());
                        }
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
        setContentView(R.layout.activity_pro_attd);

        Intent intent = getIntent();
        String course_id = intent.getStringExtra("course_id");

        ActionBar aB = getSupportActionBar();
        aB.hide(); // 타이틀 숨기기


        readExcel();

        //학생의 숫자
        STU_NUM = rowTotal-1;
        //날짜의 숫자
        DATE_NUM = colTotal-4;

        //배열 선언
        baseRow_left = new TableRow[STU_NUM];
        baseView_left = new TextView[STU_NUM][3];
        baseRow_right = new TableRow[STU_NUM+1];
        baseView_right = new TextView[STU_NUM+1][DATE_NUM];

        studentID = new String[STU_NUM];
        studentName = new String[STU_NUM];
        date = new String[DATE_NUM];
        attendance = new int[STU_NUM][DATE_NUM];
        subjectName = "["+"객체지향 설계"+"]";

        setExcel();

        //******DB 배열 초기화******

        //학번
//        for(int i=0; i<STU_NUM; i++)
//        {
//            studentID[i] = "20180380"+((int)i/10)+(i%10);
//        }
//        //학생이름
//        for(int i=0; i<STU_NUM; i++)
//        {
//            studentName[i] = "린린자자"+i;
//        }
//        //날짜 입력
//        for(int i=0; i<DATE_NUM; i++)
//        {
//            date[i] = "03/"+i;
//        }
//        //출석 정보 입력
//        for(int i=0; i<STU_NUM; i++) //학생의 숫자
//        {
//            for(int j=0; j<DATE_NUM; j++){ //날짜의 개수
//                //학생번째의 날짜(앞에서부터(
//                attendance[i][j] = i%6==0?-1:1; //1이면 출석, 0이면 지각, -1이면 결석
//            }
//        }


        //과목 이름 변경
        TextView subjectNameTV = findViewById(R.id.subjectName);
        subjectNameTV.setText(subjectName);


        //출석, 지각, 결석 횟수 변경
        TextView studentNum = findViewById(R.id.studentNum);
        studentNum.setText(Integer.toString(STU_NUM));

        //왼쪽 테이블 항목(순번, 이름, 학번( 동적으로 생성
        left_table = findViewById(R.id.tableLayoutLeft);
        TableRow.LayoutParams params_left = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        params_left.topMargin = 0;
        params_left.bottomMargin = 3;
        params_left.leftMargin = 3;
        params_left.rightMargin = 3;

        for(int i=0;i<STU_NUM; i++) { //행의 개수
            baseRow_left[i] = new TableRow(this);

            for (int j = 0; j < 3; j++) { //열의 개수
                baseView_left[i][j] = new TextView(this);
                baseView_left[i][j].setLayoutParams(params_left);
                baseView_left[i][j].setPadding(3, 3, 3, 3);
                baseView_left[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                baseView_left[i][j].setGravity(Gravity.CENTER);
                baseView_left[i][j].setTextSize(15);
                switch (j){ //text를 입력
                    case 0://순번
                        baseView_left[i][j].setText(Integer.toString(i+1));
                        break;
                    case 1://학번
                        baseView_left[i][j].setText(studentID[i]);
                        break;
                    case 2://이름
                        baseView_left[i][j].setText(studentName[i]);
                        break;
                }
                baseRow_left[i].addView(baseView_left[i][j]);
            }

            left_table.addView(baseRow_left[i]);

        }

        //오른쪽 테이블 항목(순번, 이름, 학번( 동적으로 생성
        right_table = findViewById(R.id.tableLayoutRight);
        TableRow.LayoutParams params_right_title = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        params_right_title.topMargin = 3;
        params_right_title.bottomMargin = 3;
        params_right_title.leftMargin = 3;
        params_right_title.rightMargin = 3;

        TableRow.LayoutParams params_right = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        params_right.topMargin = 0;
        params_right.bottomMargin = 3;
        params_right.leftMargin = 3;
        params_right.rightMargin = 3;

        for(int i=0;i<STU_NUM+1; i++) { //행의 개수 : 학생의 수+1(맨 위의 날짜의 수 1개 더하기)
            baseRow_right[i] = new TableRow(this);

            if(i==0)
            {
                for (int j = 0; j < DATE_NUM; j++) { //열의 개수
                    int value = 8;
                    int dpValue = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            value,
                            getResources().getDisplayMetrics());
                    baseView_right[0][j] = new TextView(this);
                    baseView_right[0][j].setLayoutParams(params_right_title);
                    baseView_right[0][j].setPadding(dpValue, dpValue, dpValue, dpValue);
                    baseView_right[0][j].setBackgroundColor(getResources().getColor(R.color.main_purple));
                    baseView_right[0][j].setGravity(Gravity.CENTER);
                    baseView_right[0][j].setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                    baseView_right[0][j].setTypeface(null, Typeface.BOLD);
                    baseView_right[0][j].setTextColor(getResources().getColor(R.color.white));
                    //날짜 설정
                    baseView_right[0][j].setText(date[j]);
                    baseRow_right[0].addView(baseView_right[i][j]);
                }
            }
            else
            {
                for (int j = 0; j < DATE_NUM; j++) { //열의 개수
                    baseView_right[i][j] = new TextView(this);
                    baseView_right[i][j].setLayoutParams(params_right);
                    baseView_right[i][j].setPadding(3, 3, 3, 3);
                    baseView_right[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                    baseView_right[i][j].setGravity(Gravity.CENTER);
                    baseView_right[i][j].setTextSize(15);
                    //출석 설정
                    switch(attendance[i-1][j]){
                        case -1:
                            baseView_right[i][j].setText("X");
                            baseView_right[i][j].setBackgroundColor(getResources().getColor(R.color.crimson));
                            break;
                        case 0:
                            baseView_right[i][j].setText("L");
                            baseView_right[i][j].setBackgroundColor(getResources().getColor(R.color.yellow));
                            break;
                        case 1:
                            baseView_right[i][j].setText("O");
                            baseView_right[i][j].setBackgroundColor(getResources().getColor(R.color.skyblue));
                            break;
                    }

                    baseRow_right[i].addView(baseView_right[i][j]);
                }
            }
            right_table.addView(baseRow_right[i]);
        }
    }
}
