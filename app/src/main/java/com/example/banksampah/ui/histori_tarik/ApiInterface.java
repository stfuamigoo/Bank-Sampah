package com.example.banksampah.ui.histori_tarik;


import com.example.banksampah.ui.transaksi_tarik.TransaksiTarik;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("get_historitarik.php?id=")
    Call<List<TransaksiTarik>> getHistoriTarik(@Query("id") String id_user);
}