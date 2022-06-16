package com.example.qr_check;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class QRcodeRegisterRequest extends StringRequest {

    final static private String URL = "http://192.168.1.151/QRCode.php";
    private Map<String, String> map;

    public QRcodeRegisterRequest(String course_id, String course_name, String pro_name, String code, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("course_id", course_id);
        map.put("course_name", course_name);
        map.put("pro_name", pro_name);
        map.put("code", code);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
