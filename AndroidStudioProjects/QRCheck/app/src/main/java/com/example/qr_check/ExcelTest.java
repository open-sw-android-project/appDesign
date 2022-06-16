package com.example.qr_check;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_test);
        readExcel();

    }//onCreate

    public void readExcel(){

        //파일 읽기
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("attd.xls");

            //엑셀파일
            Workbook wb = Workbook.getWorkbook(is);

            //엑셀 파일이 있다면
            if(wb != null){

                Sheet sheet = wb.getSheet(0);//시트 블러오기

                if(sheet != null){

                    int colTotal = sheet.getColumns(); //전체 컬럼
                    int rowIndexStart = 1; //row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal-1).length;

                    StringBuilder sb;
                    for(int row = rowIndexStart; row < rowTotal; row++){

                        sb = new StringBuilder();

                        //col: 컬럼순서, contents: 데이터값
                        for(int col = 0; col < colTotal; col++){
                            String contents = sheet.getCell(col, row).getContents();

//                            Log.d("Main", "row: " + row + "col: " + col  +"contents"  + contents);

                            if(row > 0){
                                Log.d("Main",  col + "번째: "  + contents);
                            }
                        } //내부 For

                    }//바깥 for
                }//if(sheet체크)
            }//if(wb체크)
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }
}//MainActivity
