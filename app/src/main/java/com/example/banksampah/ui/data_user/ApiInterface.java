package com.example.banksampah.ui.data_user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("tampilsemuauser.php")
    Call<List<User>> getUser();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<User> updateUser(
            @Field("key") String key,
            @Field("id") int id,
            @Field("nik") String nik,
            @Field("password") String password,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("rt") String rt,
            @Field("telepon") String telepon,
            @Field("email") String email,
            @Field("saldo") String saldo,
            @Field("role") String role);

    @FormUrlEncoded
    @POST("updatesaldo.php")
    Call<User> updateSaldo(@Field("key") String key,
                           @Field("id") String id_user,
                           @Field("saldo") int total);
}
