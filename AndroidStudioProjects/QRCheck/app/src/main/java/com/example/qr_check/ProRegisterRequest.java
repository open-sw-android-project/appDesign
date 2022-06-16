package com.example.qr_check;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProRegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.1.151/ProRegister.php";
    private Map<String, String> map;


    public ProRegisterRequest(String pro_id, String pro_password, String pro_name, String pro_department, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("pro_id", pro_id);
        map.put("pro_password", pro_password);
        map.put("pro_name", pro_name);
        map.put("pro_department", pro_department);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
