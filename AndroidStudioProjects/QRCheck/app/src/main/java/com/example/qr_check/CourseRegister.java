package com.example.qr_check;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

public class CourseRegister extends AppCompatActivity {

    private Button btn_register;
    private EditText et_semester, et_courseid, et_coursetype, et_coursename, et_coursegrade, et_courseroom,
            et_courseday1, et_day1_time, et_courseday2, et_day2_time, et_proname;
    private TimePickerDialog.OnTimeSetListener callbackMethod1, callbackMethod2;
    private WebView webView;
    private WebSettings webSettings;
    private final static int FILECHOOSER_NORMAL_REQ_CODE = 0;
    private ValueCallback<Uri[]> FilePathCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_register);

        btn_register = (Button)findViewById(R.id.btn_register);
        et_semester = (EditText) findViewById(R.id.semester);
        et_courseid = (EditText) findViewById(R.id.course_id);
        et_coursetype = (EditText) findViewById(R.id.course_type);
        et_coursename = (EditText) findViewById(R.id.course_name);
        et_coursegrade = (EditText) findViewById(R.id.course_grade);
        et_courseroom = (EditText) findViewById(R.id.course_room);
        et_courseday1 = (EditText) findViewById(R.id.course_day1);
        et_day1_time = (EditText) findViewById(R.id.day1_time);
        et_day2_time = (EditText) findViewById(R.id.day2_time);
        et_courseday2 = (EditText) findViewById(R.id.course_day2);
        et_proname = (EditText) findViewById(R.id.pro_name);

//        webView = (WebView) findViewById(R.id.attd_view);
//
//        setmWebViewFileUploadPossible();
//
//        webView.setWebViewClient(new WebViewClient());
//        webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportMultipleWindows(false);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDomStorageEnabled(true);
//        webView.loadUrl("http://192.168.0.184/webview.html");

        et_semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] arr = {"1학기", "여름학기", "2학기", "겨울학기"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseRegister.this);
                dlg.setTitle("학기를 선택해주세요");
                dlg.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_semester.setText(arr[which]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });

        et_coursetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] arr = {"전공필수", "전공선택", "교양필수", "교양선택"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseRegister.this);
                dlg.setTitle("강의유형을 선택해주세요");
                dlg.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_coursetype.setText(arr[which]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });

        et_coursegrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] arr = {"1학점", "2학점", "3학점", "4학점"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseRegister.this);
                dlg.setTitle("학점을 선택해주세요");
                dlg.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_coursegrade.setText(arr[which]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });

        et_courseday1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] arr = {"월요일", "화요일", "수요일", "목요일", "금요일"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseRegister.this);
                dlg.setTitle("날짜를 선택해주세요");
                dlg.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_courseday1.setText(arr[which]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });

        et_courseday2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] arr = {"없음", "월요일", "화요일", "수요일", "목요일", "금요일"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseRegister.this);
                dlg.setTitle("날짜를 선택해주세요");
                dlg.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_courseday2.setText(arr[which]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });

        callbackMethod1 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                et_day1_time.setText(hourOfDay + "시  " + minute + "분");
            }
        };

        callbackMethod2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                et_day2_time.setText(hourOfDay + "시  " + minute + "분");
            }
        };

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course_id = et_courseid.getText().toString();
                String semester = et_semester.getText().toString();
                String course_type = et_coursetype.getText().toString();
                String course_name = et_coursename.getText().toString();
                String pro_name = et_proname.getText().toString();
                String course_grade = et_coursegrade.getText().toString();
                String course_day1 = et_courseday1.getText().toString();
                String day1_time = et_day1_time.getText().toString();
                String course_day2 = et_courseday2.getText().toString();
                String day2_time = et_day2_time.getText().toString();
                String course_room = et_courseroom.getText().toString();
                String code;

                if(TextUtils.isEmpty(course_day2)) {
                    course_day2 = "없음";
                }

                if(TextUtils.isEmpty(day2_time)) {
                    day2_time = "없음";
                }

                MakeRandomNumber randomNum = new MakeRandomNumber("000000");

                for(int a=0; a < randomNum.randomCodeSize; a++){
                    //String ascii = Math.random();
                    int tempNum = (int) (Math.random()*100) % (randomNum.rightLimit - randomNum.leftLimit);
                    tempNum = tempNum + 65;
                    randomNum.addString((char) tempNum);
                }

                randomNum.randomCode = randomNum.randomCode + randomNum.subjectCode;

                code = randomNum.getter();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(), "강의가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CourseRegister.this, ProCourseActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "강의 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                CourseRegisterRequest courseRegisterRequest = new CourseRegisterRequest(course_id, semester, course_type, course_name, pro_name, course_grade,
                        course_day1, day1_time, course_day2, day2_time, course_room, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CourseRegister.this);
                queue.add(courseRegisterRequest);

                QRcodeRegisterRequest qRcodeRegisterRequest = new QRcodeRegisterRequest(course_id, course_name, pro_name, code, responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(CourseRegister.this);
                queue1.add(qRcodeRegisterRequest);
            }
        });
    }

    public void OnClickHandler1(View v)
    {
        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod1, 12, 00, true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void OnClickHandler2(View v)
    {
        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod2, 12, 00, true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected void setmWebViewFileUploadPossible() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                if(FilePathCallback != null) {
                    FilePathCallback.onReceiveValue(null);
                }
                FilePathCallback = filePathCallback;

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, 0);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FILECHOOSER_NORMAL_REQ_CODE)
        {
            if(resultCode == RESULT_OK) {
                FilePathCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                FilePathCallback = null;
            }
        } else {
            if(FilePathCallback != null) {
                FilePathCallback.onReceiveValue(null);
                FilePathCallback = null;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
