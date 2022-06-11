package com.example.test;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CourseRegisterRequest extends StringRequest {

    final static private String URL = "http://192.168.25.44/CourseRegister.php";
    private Map<String, String> map;

    public CourseRegisterRequest(String course_id, String semester, String course_type, String course_name, String pro_name,
                                 String course_grade, String start_date, String course_day1, String course_day2, String course_room, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("course_id", course_id);
        map.put("semester", semester);
        map.put("course_type", course_type);
        map.put("course_name", course_name);
        map.put("pro_name", pro_name);
        map.put("course_grade", course_grade);
        map.put("start_date", start_date);
        map.put("course_day1", course_day1);
        map.put("course_day2", course_day2);
        map.put("course_room", course_room);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
