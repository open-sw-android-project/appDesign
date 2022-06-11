package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProCourseActivity extends AppCompatActivity {

    private Button CourseManager;
    private TextView CourseNumber;
    private EditText SearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procourse);

        CourseManager = (Button)findViewById(R.id.course_manager);
        CourseNumber = (TextView) findViewById(R.id.course_number);
        SearchBar = (EditText) findViewById(R.id.search_bar);

        CourseManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProCourseActivity.this, CourseRegister.class);
                startActivity(intent);
            }
        });
    }
}
