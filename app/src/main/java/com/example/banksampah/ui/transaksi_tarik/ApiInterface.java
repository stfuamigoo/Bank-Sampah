package com.example.banksampah.ui.transaksi_tarik;

import com.example.banksampah.ui.data_user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
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
}
