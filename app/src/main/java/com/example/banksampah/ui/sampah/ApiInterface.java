package com.example.banksampah.ui.sampah;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("get_sampah.php")
    Call<List<Sampah>> getSampah();

    @FormUrlEncoded
    @POST("add_sampah.php")
    Call<com.example.banksampah.ui.sampah.Sampah> insertSampah(
            @Field("key") String key,
            @Field("jenissampah") String jenissampah,
            @Field("satuan") String satuan,
            @Field("harga") String harga,
            @Field("keterangan") String keterangan,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_sampah.php")
    Call<com.example.banksampah.ui.sampah.Sampah> updateSampah(
            @Field("key") String key,
            @Field("id") int id,
            @Field("jenissampah") String jenissampah,
            @Field("satuan") String satuan,
            @Field("harga") String harga,
            @Field("keterangan") String keterangan,
            @Field("picture") String picture);

}
