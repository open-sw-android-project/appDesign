package com.example.qr_check;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    CheckBox auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);
        auto_login = (CheckBox) findViewById(R.id.auto_login);

        if(auto_login.isChecked()) {
            SharedPreferences auto = getSharedPreferences("auto_login", Activity.MODE_PRIVATE);
            SharedPreferences.Editor autoLoginEdit = auto.edit();
            autoLoginEdit.putString("userId", et_id.getText().toString());
            autoLoginEdit.putString("userPassword", et_pass.getText().toString());
            autoLoginEdit.commit();

            String id = auto.getString("userId", null);
            String password = auto.getString("userPassword", null);

            if(id != null && password != null) {

                if(id.length() < 10)
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 로그인에 성공한 경우
                                    String pro_name = jsonObject.getString("pro_name");
                                    Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, ProCourseActivity.class);
                                    intent.putExtra("pro_name", pro_name);
                                    startActivity(intent);
                                } else { // 로그인에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ProLoginRequest loginRequest = new ProLoginRequest(id, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }
                else
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 로그인에 성공한 경우
                                    String std_id = jsonObject.getString("std_id");
                                    Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, StdCourseActivity.class);
                                    intent.putExtra("std_id", std_id);
                                    startActivity(intent);
                                } else { // 로그인에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    StdLoginRequest loginRequest = new StdLoginRequest(id, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }
            }
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String id = et_id.getText().toString();
                String password = et_pass.getText().toString();

                if(id.length() < 10)
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 로그인에 성공한 경우
                                    String pro_name = jsonObject.getString("pro_name");
                                    Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, ProCourseActivity.class);
                                    intent.putExtra("pro_name", pro_name);
                                    startActivity(intent);
                                } else { // 로그인에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ProLoginRequest loginRequest = new ProLoginRequest(id, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }
                else
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 로그인에 성공한 경우
                                    String std_id = jsonObject.getString("std_id");
                                    Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, StdCourseActivity.class);
                                    intent.putExtra("std_id", std_id);
                                    startActivity(intent);
                                } else { // 로그인에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    StdLoginRequest loginRequest = new StdLoginRequest(id, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public class SaveSharedPreference {

        static final String pre_user_name = "username";

        SharedPreferences getSharedPreferences(Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context);
        }

        public void setUserName(Context context, String userName) {
            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putString(pre_user_name, userName);
            editor.commit();
        }

        public String getUserName(Context context) {
            return getSharedPreferences(context).getString(pre_user_name, "");
        }

        public void clearUserName(Context context) {
            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.clear();
            editor.commit();
        }
    }
}