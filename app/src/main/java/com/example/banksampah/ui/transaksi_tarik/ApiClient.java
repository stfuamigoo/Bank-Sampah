package com.example.banksampah.ui.transaksi_tarik;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

class ApiClient {
    private static final String BASE_URL = "http://192.168.1.6/Bank-Sampah/backend/tarik/";
    public static final String URL_DELETE_TRANSAKSI_TARIK = "http://192.168.1.6/Bank-Sampah/backend/tarik/hapus_transaksi_tarik.php?id=";
    private static Retrofit retrofit;

    static Retrofit getApiClient(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
