package com.example.ass_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ass_app.ADAPTER.CommentAdapter;
import com.example.ass_app.DTO.CommentDTO;
import com.example.ass_app.DTO.TruyenDTO;
import com.example.ass_app.INTERFACE.CommnetInterFace;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChiTietActivity extends AppCompatActivity {
   private TruyenDTO truyenDTO;
    private TextView tv_name , tv_year , tv_mota ,tv_tacgia;
    private ImageView imganhbia;
    List<CommentDTO> list ;
    CommentAdapter adapter ;
    RecyclerView recyclerView ;
    Button btngui;
    EditText edtcm ;
    private Socket mSocket;
    {
        try {
            mSocket= IO.socket("http://172.20.10.4:3000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        mSocket.connect();
        tv_name = findViewById(R.id.name);
        tv_year = findViewById(R.id.tv_year);
        tv_mota = findViewById(R.id.tv_mota);
        tv_tacgia = findViewById(R.id.tv_tacgia);
        imganhbia = findViewById(R.id.img_bia);
        truyenDTO = (TruyenDTO) getIntent().getSerializableExtra("TRUYEN");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            id = bundle.getString("id_user");

        }
        Log.d("id_user", "onCreate: "+id);
        if (truyenDTO !=null){
            tv_name.setText(truyenDTO.getName());
            tv_year.setText("Năm SX : "+truyenDTO.getYear());
            tv_mota.setText("Mô tả truyện : "+truyenDTO.getMota());
            tv_tacgia.setText("Tác giả : " + truyenDTO.getTacgia());
            Picasso.get().load(truyenDTO.getImg()).into(imganhbia);
        }
        recyclerView = findViewById(R.id.recyclerViewComments);

        list = new ArrayList<CommentDTO>();
        getComment(truyenDTO.get_id());
        Button btn_read = findViewById(R.id.btn_read);
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietActivity.this, ReadActivity.class);
                intent.putExtra("truyen",truyenDTO);
                startActivity(intent);
            }
        });
        mSocket.on("new msg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                ChiTietActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data_sv_send= (String) args[0];
                        Toast.makeText(ChiTietActivity.this, "Server trả về "+data_sv_send, Toast.LENGTH_SHORT).show();

                        //hiện notify lên status
                        postNotify("Bình luận thành công",data_sv_send);
                    }
                });
            }
        });
        edtcm = findViewById(R.id.edtcm);
        btngui = findViewById(R.id.btn_gui);
        btngui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                String formattedTime = String.format(
                        "%04d-%02d-%02d %02d:%02d:%02d",
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH) + 1, // Tháng trong Java Calendar bắt đầu từ 0, nên cần +1
                        now.get(Calendar.DAY_OF_MONTH),
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND)
                );
                String commentContent = edtcm.getText().toString().trim();
                if (commentContent.isEmpty()) {
                    Toast.makeText(ChiTietActivity.this, "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();

                }else {
                    addComment(id, truyenDTO.get_id(), commentContent,formattedTime);
                }
            }
        });
    }
    static final  String BASE_URL="http://172.20.10.4:3000/binhluan/";
    private  void getComment(String id_truyen){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommnetInterFace commentInterface = retrofit.create(CommnetInterFace.class);
        Call<List<CommentDTO>> call = commentInterface.getList(id_truyen);
        call.enqueue(new Callback<List<CommentDTO>>() {
            @Override
            public void onResponse(Call<List<CommentDTO>> call, Response<List<CommentDTO>> response) {
                if (response.isSuccessful()){


                        list.clear();
                        list.addAll(response.body());
                        adapter = new CommentAdapter(list);
                    LinearLayoutManager gridLayoutManager=new LinearLayoutManager(ChiTietActivity.this);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<CommentDTO>> call, Throwable t) {

            }
        });
    }
    private  void addComment(String id_user,String id_truyen,String noidung ,String ngayGio){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommnetInterFace commnetInterFace = retrofit.create(CommnetInterFace.class);
        Call<CommentDTO>  call = commnetInterFace.addcommnet(id_user,id_truyen,noidung,ngayGio);
        call.enqueue(new Callback<CommentDTO>() {
            @Override
            public void onResponse(Call<CommentDTO> call, Response<CommentDTO> response) {
                if (response.isSuccessful()){
                 //   getComment(truyenDTO.get_id());
                    mSocket.emit("new msg","Bạn vừa bình luận truyện: "+ truyenDTO.getName());
                }
            }

            @Override
            public void onFailure(Call<CommentDTO> call, Throwable t) {

            }
        });
    }
    void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(ChiTietActivity.this, NotifyConFig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)

                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(ChiTietActivity.this);

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(ChiTietActivity.this,
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(ChiTietActivity.this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(ChiTietActivity.this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }
}