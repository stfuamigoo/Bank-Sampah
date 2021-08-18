package com.example.banksampah.ui.histori_setor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {
    private static final String BASE_URL = "http://192.168.1.4/Bank-Sampah/backend/setor/";
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
