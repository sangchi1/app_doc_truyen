package com.example.ass_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button btn_res;
    private TextInputLayout ed_user , ed_pass , ed_email , ed_fullname;
    private  String username,password,email,fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView tv_register = findViewById(R.id.tv_login);
        btn_res = findViewById(R.id.btnRegister);
        ed_user = findViewById(R.id.ed_username);
        ed_pass = findViewById(R.id.ed_password);
        ed_email = findViewById(R.id.ed_email);
        ed_fullname = findViewById(R.id.ed_fullname);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        btn_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = ed_user.getEditText().getText().toString();
                email = ed_email.getEditText().getText().toString();
                password = ed_pass.getEditText().getText().toString();
                fullname = ed_fullname.getEditText().getText().toString();
                if (username.length()==0||email.length()==0
                    || password.length()==0||fullname.length()==0){
                    Toast.makeText(RegisterActivity.this, "Khong duoc de trong", Toast.LENGTH_SHORT).show();
                } else if (email.matches(emailPattern)) {
                        RegisterUser();
                    Toast.makeText(RegisterActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }else {
                    Toast.makeText(RegisterActivity.this,"Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void RegisterUser(){
        HashMap<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("email",email);
        params.put("password",password);
        params.put("fullname",fullname);
        String api = "http://172.20.10.4:3000/users/add";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, api,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")){

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if(error instanceof ServerError && response != null){
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(RegisterActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header = new HashMap<>();
                header.put("Content-Type","application/json");
                return params;
            }
        };
        int socketTime = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}