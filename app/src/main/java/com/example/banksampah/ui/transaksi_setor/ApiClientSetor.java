package com.example.banksampah.ui.transaksi_setor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientSetor {
    private static final String BASE_URL = "https://4e02fb7b82af.ngrok.io/Bank-Sampah/backend/setor/";
    public static final String URL_DELETE_TRANSAKSI_SETOR = "https://4e02fb7b82af.ngrok.io/Bank-Sampah/backend/setor/delete_setor.php?id=";
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
