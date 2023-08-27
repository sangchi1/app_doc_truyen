package com.example.ass_app.INTERFACE;

import com.example.ass_app.DTO.CommentDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommnetInterFace {
    @GET("listForTruyen")
    Call<List<CommentDTO>> getList(@Query("id_truyen")String id_truyen);


    @FormUrlEncoded
    @POST("add")
    Call<CommentDTO> addcommnet(@Field("id_user")String id_user,@Field("id_truyen")String id_truyen,
                                @Field("noidung")String noidung,@Field("ngayGio") String ngayGio);
}
