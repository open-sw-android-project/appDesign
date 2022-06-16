package com.example.qr_check;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CourseRegisterRequest extends StringRequest{

    final static private String URL = "http://192.168.1.151/CourseRegister.php";
    private Map<String, String> map;

    public CourseRegisterRequest(String course_id, String semester, String course_type, String course_name, String pro_name, String course_grade,
                                 String course_day1, String day1_time, String course_day2, String day2_time, String course_room, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("course_id", course_id);
        map.put("semester", semester);
        map.put("course_type", course_type);
        map.put("course_name", course_name);
        map.put("pro_name", pro_name);
        map.put("course_grade", course_grade);
        map.put("course_day1", course_day1);
        map.put("day1_time", day1_time);
        map.put("course_day2", course_day2);
        map.put("day2_time", day2_time);
        map.put("course_room", course_room);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
