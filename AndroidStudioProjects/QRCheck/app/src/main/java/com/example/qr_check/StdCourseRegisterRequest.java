package com.example.qr_check;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StdCourseRegisterRequest extends StringRequest {

    final static private String URL = "http://192.168.0.184/StdRegister.php";
    private Map<String, String> map;

    public StdCourseRegisterRequest(String id, String std_id, String course_id, String course_name, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        map.put("std_id", std_id);
        map.put("course_id", course_id);
        map.put("course_name", course_name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
