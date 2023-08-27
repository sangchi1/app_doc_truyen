package com.example.ass_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ass_app.ADAPTER.ReadAdapter;
import com.example.ass_app.DTO.ReadDTO;
import com.example.ass_app.DTO.TruyenDTO;
import com.example.ass_app.INTERFACE.ReadInterface;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadActivity extends AppCompatActivity {
//    RecyclerView recyclerView ;
//    ReadAdapter adapter;
   List<ReadDTO> list;
    String id ;
    TruyenDTO tryTruyenDTO;
    ListView listView ;
    ArrayAdapter<String> adapter ;
    private List<String> data = new ArrayList<>();
       static final  String BASE_URL="http://172.20.10.4:3000/truyen/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        listView = findViewById(R.id.lv_read);
        tryTruyenDTO = (TruyenDTO) getIntent().getSerializableExtra("truyen");
        Log.d("id", "onCreate: "+tryTruyenDTO.get_id());
        adapter = new ArrayAdapter<String>(this, 0,data) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_read, parent, false);
                }

                String url = getItem(position);
                ImageView imageView = convertView.findViewById(R.id.item_read);
                Picasso.get().load(url).into(imageView);

                return convertView;
            }
        };
        listView.setAdapter(adapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReadInterface apiInterface = retrofit.create(ReadInterface.class);
        Call<ReadDTO> call = apiInterface.list(tryTruyenDTO.get_id());
        call.enqueue(new Callback<ReadDTO>() {
            @Override
            public void onResponse(Call<ReadDTO> call, Response<ReadDTO> response) {
                data.clear();
                data.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReadDTO> call, Throwable t) {

            }
        });
    }
//    static final  String BASE_URL="http://192.168.1.129:3000/truyen/";
//    private  void getRead(String id){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ReadInterface readInterface = retrofit.create(ReadInterface.class);
//        Call<List<ReadDTO>> call = readInterface.list(id);
//        call.enqueue(new Callback<List<ReadDTO>>() {
//            @Override
//            public void onResponse(Call<List<ReadDTO>> call, Response<List<ReadDTO>> response) {
//                if (response.isSuccessful()){
//                    Log.d("zzzzz", "onResponse 1: "+response.body());
//                    list.clear();
//                    list.addAll(response.body());
//                    adapter = new ReadAdapter(list);
//                    LinearLayoutManager gridLayoutManager=new LinearLayoutManager(ReadActivity.this);
//                    recyclerView.setLayoutManager(gridLayoutManager);
//                    recyclerView.setAdapter(adapter);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<ReadDTO>> call, Throwable t) {
//
//            }
//        });
//    }
}