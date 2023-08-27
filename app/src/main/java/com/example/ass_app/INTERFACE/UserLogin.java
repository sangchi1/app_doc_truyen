package com.example.ass_app.INTERFACE;

import com.example.ass_app.DTO.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserLogin {
    @POST("login")
    Call<UserDTO> user(@Body UserDTO userDTO);
}
