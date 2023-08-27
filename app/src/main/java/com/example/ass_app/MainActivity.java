package com.example.ass_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ass_app.ADAPTER.TruyenAdapter;
import com.example.ass_app.DTO.TruyenDTO;
import com.example.ass_app.DTO.UserDTO;
import com.example.ass_app.FRAGMENT.HomeFragment;
import com.example.ass_app.FRAGMENT.SettingFragment;
import com.example.ass_app.INTERFACE.TruyenInterface;
import com.example.ass_app.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String username ;
    String id ;
    String email ;
    String fullname;
    UserDTO userDTO ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());

        BottomNavigationView  bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id==R.id.home){
                replaceFragment(new HomeFragment());
            } else if (id==R.id.setting) {
                replaceFragment(new SettingFragment());
            }
            return true;
        });
        userDTO = (UserDTO) getIntent().getSerializableExtra("userData");
        Log.d("user login", "onCreate: "+userDTO.get_id()+userDTO.getUserName()+userDTO.getEmail()+userDTO.getFullname());
        id = userDTO.get_id();
        email = userDTO.getEmail();
        fullname = userDTO.getFullname();
        username = userDTO.getUserName();
    }


    private void replaceFragment(Fragment fragment){

            Bundle bundle = new Bundle();
            bundle.putString("username",username);
            bundle.putString("email",email);
            bundle.putString("id",id);
            bundle.putString("fullname",fullname);
            fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}