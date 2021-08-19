package com.example.banksampah.ui.transaksi_tarik;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceTarik {
    @POST("get_transaksitarik.php")
    Call<List<TransaksiTarik>> getTransaksiTarik();

    @FormUrlEncoded
    @POST("tambah_transaksi_tarik.php")
    Call<TransaksiTarik> insertTransaksiTarik(
            @Field("key") String key,
            @Field("tanggal_tarik") String tanggal_tarik,
            @Field("id_user") String id_user,
            @Field("nama_user") String nama_user,
            @Field("saldo_user") String saldo_user,
            @Field("jumlah_tarik") String jumlah_tarik,
            @Field("keterangan") String keterangan);

    @FormUrlEncoded
    @POST("update_transaksi_tarik.php")
    Call<TransaksiTarik> updateTransaksiTarik(
            @Field("key") String key,
            @Field("id") int id,
            @Field("tanggal_tarik") String tanggal_tarik,
            @Field("nama_user") String nama_user,
            @Field("saldo_user") String saldo_user,
            @Field("jumlah_tarik") String jumlah_tarik,
            @Field("keterangan") String keterangan);
}
