package com.example.banksampah.ui.sampah;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ApiClient {

    private static final String BASE_URL = "http://192.168.1.4/Bank-Sampah/backend/sampah/";
    public static final String URL_DELETE_SAMPAH = "http://192.168.1.4/Bank-Sampah/backend/sampah/delete_sampah.php?id=";
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
