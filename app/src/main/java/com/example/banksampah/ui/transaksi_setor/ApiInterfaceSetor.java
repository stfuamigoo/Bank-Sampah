package com.example.banksampah.ui.transaksi_setor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceSetor {
    @POST("get_setor.php")
    Call<List<TransaksiSetor>> getTransaksiSetor();

    @FormUrlEncoded
    @POST("add_setor.php")
    Call<TransaksiSetor> insertTransaksiSetor(
            @Field("key") String key,
            @Field("tanggalsetor") String tanggalsetor,
            @Field("id_user") String id_user,
            @Field("nama") String nama,
            @Field("saldo_user") String saldo_user,
            @Field("id_sampah") String id_sampah,
            @Field("jenissampah") String jenissampah,
            @Field("satuan") String satuan,
            @Field("harga") String harga,
            @Field("jumlah") String jumlah,
            @Field("total") String total,
            @Field("keterangan") String keterangan);

    @FormUrlEncoded
    @POST("update_setor.php")
    Call<TransaksiSetor> updateSetor(
            @Field("key") String key,
            @Field("id") int id,
            @Field("tanggalsetor") String tanggalsetor,
            @Field("nama") String nama,
            @Field("saldo_user") String saldo_user,
            @Field("jenissampah") String jenissampah,
            @Field("satuan") String satuan,
            @Field("harga") String harga,
            @Field("jumlah") String jumlah,
            @Field("total") String total,
            @Field("keterangan") String keterangan);
}
