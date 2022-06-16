package com.example.qr_check;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StdRegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.1.151/StdRegister.php";
    private Map<String, String> map;


    public StdRegisterRequest(String std_id, String std_password, String std_name, String std_grade, String std_department,
                              Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("std_id", std_id);
        map.put("std_password", std_password);
        map.put("std_name", std_name);
        map.put("std_grade", std_grade);
        map.put("std_department", std_department);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
