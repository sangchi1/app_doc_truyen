package com.example.ass_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.ass_app.DTO.UserDTO;
import com.example.ass_app.INTERFACE.UserLogin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout ed_username , ed_pass;
    String username , password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tv_register = findViewById(R.id.tv_register);
        ed_username =findViewById(R.id.ed_user);
        ed_pass =findViewById(R.id.ed_pass);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        Button btn = findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = ed_username.getEditText().getText().toString();
                password = ed_pass.getEditText().getText().toString();
                if (username.length()==0|| password.length()==0){
                    Toast.makeText(LoginActivity.this, "Khong duoc de trong", Toast.LENGTH_SHORT).show();
                } else {
                //   LoginUser();
                    login("http://172.20.10.4:3000/users/login");
                }

            }
        });
    }
    private  void login(String link){
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        String urlLink = link;

        service.execute(new Runnable() {
            String noidung = "";
            @Override
            public void run() {
                try {
                    URL url = new URL(urlLink);

                    //mã kết nối
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    //THiết lập phương thức POST , mặc định sẽ là GET
                    http.setRequestMethod("POST");

                    String username = ed_username.getEditText().getText().toString().trim();
                    String password = ed_pass.getEditText().getText().toString().trim();
//
//                    // Kiểm tra nhập trống
//                    if (userName.isEmpty() || password.isEmpty()) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        return;
//                    }
                    //Tạo đối tượng dữ liệu gửi lên server
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username",username);
                    jsonObject.put("password",password);


                    http.setRequestProperty("Content-Type","application/json");
                    //Tạo đối tượng out dữ liệu ra khỏi ứng dụng để gửi lên server
                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.append(jsonObject.toString());
                    //Xóa bộ đệm
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //Nhận lại dữ liệu phản hồi

                    int responseCode = http.getResponseCode();
                    if(responseCode==HttpURLConnection.HTTP_OK){
                        InputStream inputStream = http.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String dong; // dòng dữ liệu đọc được
                        // đọc dữ liệu
                        while(  (dong = reader.readLine()) != null  ){
                            builder.append( dong ).append("\n");
                        }
                        // kết thúc quá trình đọc:
                        reader.close();
                        inputStream.close();

                        // Giải mã dữ liệu JSON phản hồi từ server để lấy thông tin người dùng
                        JSONObject responseJson = new JSONObject(builder.toString());
                        String userId = responseJson.optString("userId");
                        String returnedUsername = responseJson.optString("username");
                        String returnedFullname = responseJson.optString("fullname");
                        String returnedEmail = responseJson.optString("email");
                        // Tạo đối tượng UserData để truyền sang màn hình Home
                        UserDTO userData = new UserDTO( userId,returnedUsername,returnedEmail,returnedFullname);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userData", userData);
                                startActivity(intent);
                                finish(); // Đóng màn hình đăng nhập
                            }
                        });

                    } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }


        });

    }
//    private  void LoginUser(){
//     final    HashMap<String,String> params = new HashMap<>();
//        params.put("username",username);
//        params.put("password",password);
//
//        String api = "http://192.168.1.129:3000/users/login";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, api,
//                new JSONObject(params), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    if (response.getBoolean("check")){
//                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//
//
//                    }
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse response = error.networkResponse;
//                if(error instanceof ServerError && response != null){
//                    try {
//                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
//                        JSONObject obj = new JSONObject(res);
//                     //   Toast.makeText(LoginActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String,String> header = new HashMap<>();
//                header.put("Content-Type","application/json");
//                return params;
//            }
//        };
//        int socketTime = 3000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTime,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        jsonObjectRequest.setRetryPolicy(policy);
//        RequestQueue requestQueue  = Volley.newRequestQueue(this);
//        requestQueue.add(jsonObjectRequest);
//    }

}