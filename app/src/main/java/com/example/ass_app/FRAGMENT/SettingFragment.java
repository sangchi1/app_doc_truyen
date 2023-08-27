package com.example.ass_app.FRAGMENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ass_app.DTO.TruyenDTO;
import com.example.ass_app.DTO.UserDTO;
import com.example.ass_app.LoginActivity;
import com.example.ass_app.R;

import java.util.ArrayList;
import java.util.Set;

public class SettingFragment extends Fragment {
    TextView tvemail ;
    TextView tvfullname ;
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.fragment_setting, container, false);
        tvemail = view.findViewById(R.id.tv_email);
        tvfullname = view.findViewById(R.id.tv_Fullname);
        if(getArguments()!=null){
            String email = getArguments().getString("email");
            String fullname = getArguments().getString("fullname");
            tvemail.setText("Email : "+email);
            tvfullname.setText("Full name : "+fullname);
        }
        btn = view.findViewById(R.id.btnLogout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
