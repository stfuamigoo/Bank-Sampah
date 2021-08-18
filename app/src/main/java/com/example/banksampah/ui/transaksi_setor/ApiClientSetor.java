package com.example.banksampah.ui.transaksi_setor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientSetor {
    private static final String BASE_URL = "http://192.168.1.4/Bank-Sampah/backend/setor/";
    public static final String URL_DELETE_TRANSAKSI_SETOR = "http://192.168.1.4/Bank-Sampah/backend/setor/delete_setor.php?id=";
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
