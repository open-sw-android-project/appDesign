package com.example.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_stdid, et_proid, et_pass, et_name, et_grade, et_department;
    private RadioGroup radioGroup;
    private RadioButton rbt_std, rbt_pro;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_stdid = findViewById(R.id.et_stdid);
        et_proid = findViewById(R.id.et_proid);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_grade = findViewById(R.id.et_grade);
        et_department = findViewById(R.id.et_department);

        radioGroup = findViewById(R.id.radioGroup);
        btn_register = findViewById(R.id.btn_register);
        rbt_std = findViewById(R.id.rbt_std);
        rbt_pro = findViewById(R.id.rbt_pro);

        et_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] arr = {"1학년", "2학년", "3학년", "4학년"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterActivity.this);
                dlg.setTitle("학년을 선택해주세요");
                dlg.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_grade.setText(arr[which]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alterDialog = dlg.create();
                alterDialog.show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbt_std || rbt_std.isChecked() == true) {
                    et_stdid.setVisibility(View.VISIBLE);
                    et_stdid.setEnabled(true);
                    et_proid.setVisibility(View.INVISIBLE);
                    et_proid.setEnabled(false);
                    et_grade.setVisibility(View.VISIBLE);

                    btn_register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                            String std_id = et_stdid.getText().toString();
                            String std_password = et_pass.getText().toString();
                            String std_name = et_name.getText().toString();
                            String std_grade = et_grade.getText().toString();
                            String std_department = et_department.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) { // 회원등록에 성공한 경우
                                            Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else { // 회원등록에 실패한 경우
                                            Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            // 서버로 Volley를 이용해서 요청을 함.
                            StdRegisterRequest registerRequest = new StdRegisterRequest(std_id, std_password, std_name, std_grade, std_department,
                                    responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);
                        }
                    });
                } else if (checkedId == R.id.rbt_pro) {
                    et_stdid.setVisibility(View.INVISIBLE);
                    et_stdid.setEnabled(false);
                    et_proid.setVisibility(View.VISIBLE);
                    et_proid.setEnabled(true);
                    et_grade.setVisibility(View.INVISIBLE);

                    btn_register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                            String pro_id = et_proid.getText().toString();
                            String pro_password = et_pass.getText().toString();
                            String pro_name = et_name.getText().toString();
                            String pro_department = et_department.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) { // 회원등록에 성공한 경우
                                            Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else { // 회원등록에 실패한 경우
                                            Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            // 서버로 Volley를 이용해서 요청을 함.
                            ProRegisterRequest registerRequest = new ProRegisterRequest(pro_id, pro_password, pro_name, pro_department,
                                    responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);
                        }
                    });
                }
            }
        });
    }
}