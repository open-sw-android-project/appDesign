package com.cookandroid.dynamictest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    EditText input;
    Button btn;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText)findViewById(R.id.inputNumber);
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                number = Integer.parseInt(input.getText().toString());
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                // 입력숫자 넘기기
                intent.putExtra("input", number);
                finish();
            }
        });
    }







}