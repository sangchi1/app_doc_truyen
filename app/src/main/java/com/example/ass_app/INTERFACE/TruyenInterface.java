package com.example.ass_app.INTERFACE;

import com.example.ass_app.DTO.TruyenDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TruyenInterface {
    @GET("list")
    Call<List<TruyenDTO>> list() ;
}
