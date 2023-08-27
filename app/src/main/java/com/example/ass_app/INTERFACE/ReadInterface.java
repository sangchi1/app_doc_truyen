package com.example.ass_app.INTERFACE;

import com.example.ass_app.DTO.ReadDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReadInterface {
    @GET("read/{id}")
    Call<ReadDTO> list(@Path("id") String id);

}
